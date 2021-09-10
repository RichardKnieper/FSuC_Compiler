package ast.node.type;

import ast.VariableType;

/**
 * Represents the type definition of an array that contains values of a given {@link VariableType}.
 */
public class ArrayTypeNode extends TypeNode {
	public ArrayTypeNode(VariableType elementType) {
		super(new VariableType.ArrayVariableType(elementType));
	}

	public String toString(String indent) {
		return indent + "ArrayTypeNode" + "\n";
	}
}
