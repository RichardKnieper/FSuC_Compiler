package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import jj.Token;

import java.util.List;

public class ReadClassAttributeNode extends AtomNode {
	Token classIdentifier, attributeIdentifier;

	public ReadClassAttributeNode(Token classIdentifier, Token attributeIdentifier) {
		this.classIdentifier = classIdentifier;
		this.attributeIdentifier = attributeIdentifier;
	}

	public String toString(String indent) {
		return indent + "ReadClassAttributeNode: " + classIdentifier + "." + attributeIdentifier + "\n";
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (tabelle.find(classIdentifier.image) == null) {
			errors.add(new CompilerError("Error: Class " + classIdentifier.image + " does not exist"));
		}
	}
}
