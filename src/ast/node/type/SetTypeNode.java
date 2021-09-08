package ast.node.type;

import ast.VariableType;

public class SetTypeNode extends TypeNode {
	public SetTypeNode(VariableType elementType) {
		super(new VariableType.SetVariableType(elementType));
	}

	public String toString(String indent) {
		return indent + "SetTypeNode" + "\n";
	}
}
