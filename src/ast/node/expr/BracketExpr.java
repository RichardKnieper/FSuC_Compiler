package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;

import java.util.List;

public class BracketExpr extends ExprNode {
	public ExprNode expr;

	public BracketExpr(ExprNode expr) {
		this.expr = expr;
	}

	public String toString(String indent) {
		return indent + "BracketExpr " + "\n" + ((expr != null) ? expr.toString(indent + "\t") : "");
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
		return exprType;
	}
}
