package ast.node.type;

import ast.VariableType;

public class MapTypeNode extends TypeNode {
	public MapTypeNode(VariableType key, VariableType value) {
		super(new VariableType.MapVariableType(key, value));
	}

	public String toString(String indent) {
		return indent + "MapTypeNode" + "\n";
	}
}
