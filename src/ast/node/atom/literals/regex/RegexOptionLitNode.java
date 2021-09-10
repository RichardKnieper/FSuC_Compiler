package ast.node.atom.literals.regex;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.value.Value;
import jj.Token;

import java.util.List;

public class RegexOptionLitNode extends AtomNode {
    RegexAtomLitNode regex;
    Token op;

    public RegexOptionLitNode(RegexAtomLitNode regex, Token op) {
        this.regex = regex;
        this.op = op;
    }

    @Override
    public String toString(String indent) {
        return null;
    }

    @Override
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        return null;
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        return null;
    }
}
