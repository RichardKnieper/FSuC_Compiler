package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import jj.Token;

import java.util.List;

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
			VariableType secondExpr = expr.semantischeAnalyse(tabelle, errors);
			if (exprType.hasSameTypeAs(secondExpr) && exprType.hasSameTypeAs(VariableType.intT)) {
				return exprType;
			} else {
				errors.add(new CompilerError(
						"Error: Operation " + op.image + " in line " + op.beginLine + " only accepts values of type Integer."));
				return VariableType.errorT;
			}
		}
		return exprType;
	}

}
