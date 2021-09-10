package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.value.Value;
import domain.BasicRange;
import domain.FiniteAutomata;
import domain.Range;
import jj.Token;

import java.util.List;

/**
 * Represents addition and subtraction of integers, union or two ranges and addition of a transition to a finite automata.
 */
public class SumExprNode extends ExprNode {
	public ProdExprNode expr;
	public Token op;
	public ProdExprNode secondExpr;

	public SumExprNode(ProdExprNode expr, Token op, ProdExprNode secondExpr) {
		this.expr = expr;
		this.op = op;
		this.secondExpr = secondExpr;
	}

	public String toString(String indent) {
		return indent + "SumExprNode " + ((op != null) ? op.image : "") + "\n"
				+ ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
	}

	@SuppressWarnings("DuplicatedCode")
	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
		if (secondExpr != null) {
			VariableType secondExprType = expr.semantischeAnalyse(tabelle, errors);

			if (op.image.equals("+")) {
				if (exprType.hasSameTypeAs(secondExprType) && exprType.hasSameTypeAs(VariableType.intT)) {
					return VariableType.intT;
				} else if (exprType.hasSameTypeAs(secondExprType) && exprType.hasSameTypeAs(VariableType.rangeT)) {
					return VariableType.rangeT;
				} else if (exprType.hasSameTypeAs(VariableType.faT) && secondExprType.hasSameTypeAs(VariableType.faT)) {
					return VariableType.faT;
				} else if (exprType.hasSameTypeAs(VariableType.faT) && secondExprType.hasSameTypeAs(VariableType.transitionT)) {
					return VariableType.faT;
				} else {
					errors.add(new CompilerError(
							"Error: Operation " + op.image + " in line " + op.beginLine + " only accepts Integer, Range or FA + Transition."));
					return VariableType.errorT;
				}
			} else { // op = "-"
				if (exprType.hasSameTypeAs(secondExprType) && exprType.hasSameTypeAs(VariableType.intT)) {
					return VariableType.intT;
				} else {
					errors.add(new CompilerError(
							"Error: Operation " + op.image + " in line " + op.beginLine + " only accepts values of type Integer."));
					return VariableType.errorT;
				}
			}
		}
		return exprType;
	}

	@SuppressWarnings("DuplicatedCode")
	@Override
	public Value run(SymbolTabelle tabelle) {
		if (op != null)  {
			Value firstValue = expr.run(tabelle);
			Value secondValue = secondExpr.run(tabelle);

			if (firstValue.type.hasSameTypeAs(VariableType.identifier)) {
				firstValue = tabelle.find(firstValue.identifier.image).value;
			}
			if (secondValue.type.hasSameTypeAs(VariableType.identifier)) {
				secondValue = tabelle.find(secondValue.identifier.image).value;
			}

			if (secondExpr != null) {
				if (op.image.equals("+")) {
					if (firstValue.type.hasSameTypeAs(secondValue.type) && firstValue.type.hasSameTypeAs(VariableType.intT)) {
						return new Value(firstValue.i + secondValue.i);
					} else if (firstValue.type.hasSameTypeAs(secondValue.type) && firstValue.type.hasSameTypeAs(VariableType.rangeT)) {
						Range range = null; // can not be initialized here because otherwise the add(...) method does not work

						for (BasicRange br : firstValue.r.getElement()) {
							if (range == null) {
								range = new Range(br.getStart(), br.getEnd());
							} else {
								range.add(br.getStart(), br.getEnd());
							}
						}

						for (BasicRange br : secondValue.r.getElement()) {
							if (range == null) {
								range = new Range(br.getStart(), br.getEnd());
							} else {
								range.add(br.getStart(), br.getEnd());
							}
						}

						return new Value(range);
					} else if (firstValue.type.hasSameTypeAs(VariableType.faT) && secondValue.type.hasSameTypeAs(VariableType.faT)) {
						return new Value(FiniteAutomata.union(firstValue.fa.getStartState(), firstValue.fa, secondValue.fa));
					} else { // FA + Transition
						FiniteAutomata fa = new FiniteAutomata(firstValue.fa.getStartState());
						firstValue.fa
								.getTransitions()
								.forEach(fa::addTransitions);
						fa.addTransitions(secondValue.transition);
						return new Value(fa);
					}
				} else { // op = "-"
					return new Value(firstValue.i - secondValue.i);
				}
			}
		}
		return expr.run(tabelle);
	}
}
