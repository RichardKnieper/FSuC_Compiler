package ast.node;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CuNode extends Node {
    public List<Node> declOrStmntList = new LinkedList<>();

    public String toString(String indent) {
        String res = indent + "CompilationUnit";
        for (Node node : declOrStmntList)
            res += "\n" + ((node != null) ? node.toString(indent + "\t") : "");
        return res;
    }

    public void semantischeAnalyse(List<CompilerError> errors) {
        semantischeAnalyse(new SymbolTabelle(null), errors);
    }

    public VariableType semantischeAnalyse() {
        return semantischeAnalyse(new SymbolTabelle(null), Collections.emptyList());
    }

    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        for (Node node : declOrStmntList) {
            node.semantischeAnalyse(tabelle, errors);
        }
        errors.forEach(error -> System.out.print(error.toString()));
        if (errors.isEmpty()) {
            return VariableType.noReturnType;
        } else {
            return VariableType.errorT;
        }
    }
}
