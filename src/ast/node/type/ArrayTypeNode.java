package ast.node.type;

import ast.VariableType;

public class ArrayTypeNode extends TypeNode {
	public ArrayTypeNode(VariableType elementType) {
		super(new VariableType.ArrayVariableType(elementType));
	}

	public String toString(String indent) {
		return indent + "ArrayTypeNode" + "\n";
	}
}
