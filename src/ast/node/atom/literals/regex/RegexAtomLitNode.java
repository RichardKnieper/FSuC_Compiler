package ast.node.atom.literals.regex;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.atom.literals.RangeLitNode;
import ast.value.Value;
import jj.Token;

import java.util.List;

public class RegexAtomLitNode extends AtomNode {
    public RangeLitNode range;
    public RegexOrLitNode bracketed;
    public Token s;

    public RegexAtomLitNode(RangeLitNode range, RegexOrLitNode bracketed, Token s) {
        this.range = range;
        this.bracketed = bracketed;
        this.s = s;
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
