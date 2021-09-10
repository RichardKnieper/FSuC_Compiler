package ast.node.atom.literals.regex;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.value.Value;

import java.util.List;

public class RegexLitNode extends AtomNode {
    public RegexOrLitNode regex;

    public RegexLitNode(RegexOrLitNode regex) {
        this.regex = regex;
    }

    @Override
    public String toString(String indent) {
        return null;
    }

    @Override
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        VariableType regexType = regex.semantischeAnalyse(tabelle, errors);
    	return regexType;
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        return null;
    }
}
