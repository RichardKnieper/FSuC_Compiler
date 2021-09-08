package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.expr.ExprNode;

import java.util.List;

public class ReturnStmntNode extends StmntNode {
	public ExprNode expr;

	public ReturnStmntNode(ExprNode expr) {
		super();
		this.expr = expr;
	}

	public String toString(String indent) {
		String res = indent + "Return Statement\n";
		expr.toString(indent + "\t");
		return res;
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		expr.semantischeAnalyse(tabelle, errors);
	}
}
