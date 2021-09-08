package ast.node.type;

import ast.VariableType;

//Todo typeNode
public class SetTypeNode extends TypeNode {
	public SetTypeNode(VariableType variableType) {
		super(variableType);
	}

	public String toString(String indent) {
		return indent + "SetTypeNode" + "\n";
	}
}
