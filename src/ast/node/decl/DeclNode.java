package ast.node.decl;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		type.semantischeAnalyse(tabelle, errors);

		String varName = identifier.image;
		if (tabelle.findInCurrentBlock(varName) != null) {
			errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
					+ tabelle.find(identifier.image).identifier.beginLine));
			return VariableType.errorT;
		} else {
			tabelle.add(varName, this);
			return VariableType.noReturnType;
		}
	}
}
