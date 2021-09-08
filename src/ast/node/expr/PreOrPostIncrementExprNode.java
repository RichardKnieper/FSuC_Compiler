package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.node.atom.AtomNode;
import jj.Token;

import java.util.List;

public class PreOrPostIncrementExprNode extends ExprNode {
	public AtomNode atom;
	public Token pre;
	public Token post;

	public PreOrPostIncrementExprNode(AtomNode atom, Token pre, Token post) {
		this.atom = atom;
		this.pre = pre;
		this.post = post;
	}

	public String toString(String indent) {
		return indent + "IntersectionExprNode " + "\n" + atom.toString(indent + "\t") + "\n"
				+ ((pre != null) ? pre.image : "") + ((post != null) ? post.image : "");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		atom.semantischeAnalyse(tabelle, errors);
		realType = atom.realType;
	}
}
