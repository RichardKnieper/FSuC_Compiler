package ast.node.type;

import ast.VariableType;

/**
 * Represents the type definition of a map that maps keys of a given {@linke VariableType} to values of a given {@linke VariableType}.
 */
public class MapTypeNode extends TypeNode {
	public MapTypeNode(VariableType key, VariableType value) {
		super(new VariableType.MapVariableType(key, value));
	}

	public String toString(String indent) {
		return indent + "MapTypeNode" + "\n";
	}
}
