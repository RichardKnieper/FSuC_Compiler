package ast.node;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.value.Value;

import java.util.List;

/**
 * Generic node of the AST.
 */
abstract public class Node {
    public abstract String toString(String indent);

    public abstract VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors);
    public abstract Value run(SymbolTabelle tabelle);
}
