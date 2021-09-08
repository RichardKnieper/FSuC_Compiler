package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.node.atom.AtomNode;
import jj.Token;

import java.util.List;

public class StateLitNode extends AtomNode {
	public Token label, accept;

	public StateLitNode(Token label, Token accept) {
		super();
		this.label = label;
		this.accept = accept;
	}

	public String toString(String indent) {
		return indent + "StateLitNode: " + label.image + ((accept != null) ? accept.image : "");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (accept != null && accept.kind == 40) {
			if (tabelle.find(accept.image) == null)
				errors.add(new CompilerError("Error: Class " + accept.image + " does not exist"));
		}
	}
}
