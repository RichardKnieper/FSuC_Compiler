package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import jj.Token;

import java.util.List;

public class AndOrExpr extends ExprNode {
	public CompExprNode expr;
	public Token op;
	public CompExprNode secondExpr;

	public AndOrExpr(CompExprNode expr, Token op, CompExprNode secondExpr) {
		this.expr = expr;
		this.op = op;
		this.secondExpr = secondExpr;
	}

	public String toString(String indent) {
		return indent + "AndOrExpr " + "\n" + ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		expr.semantischeAnalyse(tabelle, errors);
		if (secondExpr != null) {
			secondExpr.semantischeAnalyse(tabelle, errors);
			if (VariableType.hasSameTypeAs(expr.realType, secondExpr.realType)) {
				realType = expr.realType;
			} else {
				realType = VariableType.errorT;
				errors.add(new CompilerError("Error: Type by " + op.image + " in line " + op.beginLine + " mismatch"));
			}
		}
	}
}
