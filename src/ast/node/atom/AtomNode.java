package ast.node.atom;

import ast.VariableType;
import ast.node.Node;

abstract public class AtomNode extends Node {
	public VariableType realType = VariableType.errorT;
}
