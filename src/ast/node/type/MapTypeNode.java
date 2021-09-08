package ast.node.type;

import ast.VariableType;

public class MapTypeNode extends TypeNode {
	VariableType key, value;

	public MapTypeNode(VariableType key, VariableType value) {
		super(null);
		this.key = key;
		this.value = value;
	}

	public String toString(String indent) {
		return indent + "MapTypeNode" + "\n";
	}
}
