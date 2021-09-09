package ast.node.expr;

import ast.SymbolTabelle;
import ast.VariableType;
import ast.exceptions.CompilerError;
import ast.value.Value;

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
		return expr.semantischeAnalyse(tabelle, errors);
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		return expr.run(tabelle);
	}
}
