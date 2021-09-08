package ast.node.type;

import ast.VariableType;
import ast.node.Node;

//Todo typeNode
public class TypeNode extends Node {
	public VariableType variableType;

	public TypeNode(VariableType variableType) {
		super();
		this.variableType = variableType;
	}



	public String toString(String indent) {
		if (variableType.sameTypeAs(variableType, VariableType.intT)) {

		}
		return indent + "TypeNode" + "\n";
	}
}
