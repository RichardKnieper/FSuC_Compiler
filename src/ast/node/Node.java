package ast.node;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;

import java.util.List;

abstract public class Node {
    public abstract String toString(String indent);

    public abstract VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors);
}
