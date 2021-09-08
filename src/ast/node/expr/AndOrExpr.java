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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
		if (secondExpr != null) {
			VariableType secondExprType = secondExpr.semantischeAnalyse(tabelle, errors);
			if (secondExprType.hasSameTypeAs(exprType)) {
				if(secondExprType.hasSameTypeAs(VariableType.booleanT)){
					return VariableType.booleanT;
				} else {
					errors.add(new CompilerError("Error: Type for operator " + op.image + " in line " + op.beginLine + " must be boolean."));
					return VariableType.errorT;
				}
			} else {
				errors.add(new CompilerError("Error: Type mismatch for operator " + op.image + " in line " + op.beginLine));
				return VariableType.errorT;
			}
		}
		return exprType;
	}
}
