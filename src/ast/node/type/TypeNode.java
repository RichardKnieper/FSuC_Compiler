package ast.node.type;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.Node;

import java.util.List;

public class TypeNode extends Node {
	public VariableType variableType;

	public TypeNode(VariableType variableType) {
		super();
		this.variableType = variableType;
	}

	public String toString(String indent) {
//		if (variableType.hasSameTypeAs(variableType, VariableType.intT)) {
//
//		}
		return indent + "TypeNode" + "\n";
	}

	@Override
	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		return variableType;
	}
}
