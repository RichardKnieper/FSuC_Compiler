package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import jj.Token;

import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class CompExprNode extends ExprNode {
	public SumExprNode expr;
	public Token op;
	public SumExprNode secondExpr;

	public CompExprNode(SumExprNode expr, Token op, SumExprNode secondExpr) {
		this.expr = expr;
		this.op = op;
		this.secondExpr = secondExpr;
	}

	public String toString(String indent) {
		return indent + "CompExprNode " + ((op != null) ? op.image : "") + "\n"
				+ ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
		if (secondExpr != null) {
			VariableType secondExprType = secondExpr.semantischeAnalyse(tabelle, errors);
			if (secondExprType.hasSameTypeAs(exprType)) {
				if (op.image.equals("!=") || op.image.equals("==")) {
					if ((secondExprType.hasSameTypeAs(VariableType.booleanT))
							|| (secondExprType.hasSameTypeAs(VariableType.intT))) {
						return VariableType.booleanT;
					} else {
						errors.add(new CompilerError("Error: Comparison in line " + op.beginLine + ", " + op.image
								+ " accepts only Boolean or Integer."));
						return VariableType.errorT;
					}
				} else {
					if (secondExprType.hasSameTypeAs(VariableType.intT)) {
						return VariableType.booleanT;
					} else {
						errors.add(new CompilerError(
								"Error: Comparison in line " + op.beginLine + ", " + op.image + " accepts only Integer."));
						return VariableType.errorT;
					}
				}
			} else {
				errors.add(new CompilerError("Error: Type mismatch for operator " + op.image + " in line " + op.beginLine));
				return VariableType.errorT;
			}
		}
		return exprType;
	}
}
