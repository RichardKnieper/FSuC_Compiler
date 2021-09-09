package ast.node;

import ast.SymbolTabelle;
import ast.VariableType;
import ast.exceptions.CompilerError;
import ast.value.Value;

import java.util.List;

abstract public class Node {
    public abstract String toString(String indent);

    public abstract VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors);
    public abstract Value run(SymbolTabelle tabelle);
}
