package ast.node.type;

import ast.VariableType;

public class ArrayTypeNode extends TypeNode {
	public ArrayTypeNode(VariableType variableType) {
		super(variableType);
	}

	public String toString(String indent) {
		return indent + "ArrayTypeNode" + "\n";
	}
}
