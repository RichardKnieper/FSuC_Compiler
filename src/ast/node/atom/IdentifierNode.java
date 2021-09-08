package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import jj.Token;

import java.util.List;

public class IdentifierNode extends AtomNode {
	public Token identifier;

	public IdentifierNode(Token identifier) {
		this.identifier = identifier;
	}

	public String toString(String indent) {
		return indent + "IdentifierNode: " + identifier.image;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (tabelle.find(identifier.image) == null)
			errors.add(new CompilerError("Error: " + identifier.image + " cannot be resolved to a variable"));
	}
}
