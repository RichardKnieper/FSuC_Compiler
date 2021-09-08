package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;

import java.util.List;

public class BracketExpr extends ExprNode {
	public ExprNode expr;

	public BracketExpr(ExprNode expr) {
		this.expr = expr;
	}

	public String toString(String indent) {
		return indent + "BracketExpr " + "\n" + ((expr != null) ? expr.toString(indent + "\t") : "");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		expr.semantischeAnalyse(tabelle, errors);
	}
}
