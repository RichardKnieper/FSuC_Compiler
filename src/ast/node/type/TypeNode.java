package ast.node.type;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.Node;
import ast.value.Value;

import java.util.List;

/**
 * Represents the type definition of a variable.
 */
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

	// method should never be executed
	@Override
	public Value run(SymbolTabelle tabelle) {
		return null;
	}
}
