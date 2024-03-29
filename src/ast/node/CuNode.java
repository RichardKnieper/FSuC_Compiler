package ast.node;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.stmnt.ReturnStmntNode;
import ast.value.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contains a list of all declarations and statements of the script
 * Starts syntax check and interpreter methods.
 * Checks if last statement is a return statement and if it returns a {@link domain.FiniteAutomata}.
 */
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
        List<CompilerError> errors = new LinkedList<>();
        VariableType result = semantischeAnalyse(new SymbolTabelle(null), errors);
        errors.forEach(error -> System.out.println(error.toString()));
        return result;
    }

    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        List<VariableType> types = declOrStmntList.stream()
                .filter(Objects::nonNull)
                .map(node -> node.semantischeAnalyse(tabelle, errors))
                .collect(Collectors.toList());

        if (!(declOrStmntList.get(declOrStmntList.size() - 1) instanceof ReturnStmntNode)) {
            errors.add(new CompilerError("Error: The last statement be a return statement."));
            types.add(VariableType.errorT);
        }
        if (!types.get(types.size() - 1).hasSameTypeAs(VariableType.faT)) {
            errors.add(new CompilerError("Error: The last statement must return an object of the FA class."));
            types.add(VariableType.errorT);
        }

        return types.contains(VariableType.errorT) ? VariableType.errorT : VariableType.faT;
    }

    public Value run() {
        return run(new SymbolTabelle(null));
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        return declOrStmntList.stream()
                .map(node -> node.run(tabelle))
                .collect(Collectors.toList())
                .get(declOrStmntList.size() - 1);
    }
}
