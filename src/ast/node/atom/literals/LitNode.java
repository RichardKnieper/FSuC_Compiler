package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import jj.Token;

import java.util.List;

public class LitNode extends AtomNode {
	public Token token;

	public LitNode(Token token) {
		super();
		this.token = token;
	}

	public String toString(String indent) {
		return indent + "LitNode: " + token.image;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		switch (token.kind) {
		case 37:
			realType = VariableType.booleanT;
			break;
		case 38:
			realType = VariableType.charT;
			break;
		case 39:
			realType = VariableType.stringT;
			break;
		case 40:
			realType = VariableType.intT;
			break;
		default:
			realType = VariableType.errorT;
			break;
		}
	}
}
