package ast.node.decl;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.node.Node;
import ast.node.type.TypeNode;
import jj.Token;

import java.util.List;

public class DeclNode extends Node {
	public TypeNode type;
	public Token identifier;

	public DeclNode(TypeNode type, Token identifier) {
		super();
		this.type = type;
		this.identifier = identifier;
	}

	public String toString(String indent) {
		return indent + "DeclNode\n" + type.toString(indent + "\t") + "\n" + identifier.image + "\n";
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {

		type.semantischeAnalyse(tabelle, errors);

		if (!tabelle.add(identifier.image, this))
			errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
					+ tabelle.find(identifier.image).identifier.beginLine));
	}
}
