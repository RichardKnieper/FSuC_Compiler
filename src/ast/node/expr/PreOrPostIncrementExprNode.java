package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType atomType = atom.semantischeAnalyse(tabelle, errors);
		if (post != null) {
			if (atomType.hasSameTypeAs(VariableType.intT))
				return VariableType.intT;
			else {
				errors.add(new CompilerError("Error: Postoperation " + post.image + " in line " + post.beginLine
						+ " needs to be a value of type Integer."));
				return VariableType.errorT;
			}
		}
		if (pre != null) {
			if (atomType.hasSameTypeAs(VariableType.intT))
				return VariableType.intT;
			else {
				errors.add(new CompilerError("Error: Postoperation " + pre.image + " in line " + pre.beginLine
						+ " needs to be a value of type Integer."));
				return VariableType.errorT;
			}
		}
		return atomType;
	}
}
