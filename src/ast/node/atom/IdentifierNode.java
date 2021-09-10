package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.decl.DeclNode;
import ast.value.Value;
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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		DeclNode temp = tabelle.find(identifier.image);
		if (temp == null) {
			errors.add(new CompilerError("Error: " + identifier.image + " cannot be resolved to a variable"));
			return VariableType.errorT;
		}
		return temp.type.variableType;
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		Value returnValue = new Value();
		returnValue.type = VariableType.identifier;
		returnValue.identifier = identifier;
		return returnValue;
	}
}
