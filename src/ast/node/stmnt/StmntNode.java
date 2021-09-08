package ast.node.stmnt;

import ast.VariableType;
import ast.node.Node;

public abstract class StmntNode extends Node {
    public VariableType realType = VariableType.errorT;
}
