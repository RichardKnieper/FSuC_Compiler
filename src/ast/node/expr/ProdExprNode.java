package ast.node.expr;

import ast.SymbolTabelle;
import ast.VariableType;
import ast.exceptions.CompilerError;
import ast.value.Value;
import jj.Token;

import java.util.List;

public class ProdExprNode extends ExprNode {
	public IntersectionExprNode expr;
	public Token op;
	public IntersectionExprNode secondExpr;

	public ProdExprNode(IntersectionExprNode expr, Token op, IntersectionExprNode secondExpr) {
		this.expr = expr;
		this.op = op;
		this.secondExpr = secondExpr;
	}

	public String toString(String indent) {
		return indent + "ProdExprNode " + ((op != null) ? op.image : "") + "\n"
				+ ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
	}

	@SuppressWarnings("DuplicatedCode")
	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
		if (secondExpr != null) {
			VariableType secondExpr = expr.semantischeAnalyse(tabelle, errors);
			if (exprType.hasSameTypeAs(secondExpr) && exprType.hasSameTypeAs(VariableType.intT)) {
				return VariableType.intT;
			} else {
				errors.add(new CompilerError(
						"Error: Operation " + op.image + " in line " + op.beginLine + " only accepts values of type Integer."));
				return VariableType.errorT;
			}
		}
		return exprType;
	}

	@SuppressWarnings("DuplicatedCode")
	@Override
	public Value run(SymbolTabelle tabelle) {
		if (op == null) {
			return expr.run(tabelle);
		} else {
			Value firstValue = expr.run(tabelle);
			Value secondValue = secondExpr.run(tabelle);

			if (firstValue.type.hasSameTypeAs(VariableType.identifier)) {
				firstValue = tabelle.find(firstValue.identifier.image).value;
			}
			if (secondValue.type.hasSameTypeAs(VariableType.identifier)) {
				secondValue = tabelle.find(secondValue.identifier.image).value;
			}

			int returnValue;
			if (op.image.equals("*")) {
				returnValue = firstValue.i * secondValue.i;
			} else { // op = "/"
				returnValue = firstValue.i / secondValue.i;
			}
			return new Value(returnValue);
		}
	}
}
