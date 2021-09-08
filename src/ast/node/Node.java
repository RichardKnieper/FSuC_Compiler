package ast.node;

import ast.CompilerError;
import ast.SymbolTabelle;

import java.util.List;

abstract public class Node {
    public abstract String toString(String indent);

    public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
    }
}
