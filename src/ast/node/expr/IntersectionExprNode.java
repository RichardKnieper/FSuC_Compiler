package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;

import java.util.List;

public class IntersectionExprNode extends ExprNode {
	public PreOrPostIncrementExprNode a;
	public PreOrPostIncrementExprNode b;

	public IntersectionExprNode(PreOrPostIncrementExprNode a, PreOrPostIncrementExprNode b) {
		this.a = a;
		this.b = b;
	}

	public String toString(String indent) {
		return indent + "IntersectionExprNode " + "\n" + ((a != null) ? a.toString(indent + "\t") : "") + "\n"
				+ ((b != null) ? b.toString(indent + "\t") : "");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		a.semantischeAnalyse(tabelle, errors);
		if (b != null)
			b.semantischeAnalyse(tabelle, errors);
	}
}
