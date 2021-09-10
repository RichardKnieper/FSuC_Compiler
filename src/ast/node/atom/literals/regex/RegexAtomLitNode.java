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
        if(range!=null) {
        	return VariableType.rangeT;
        }else if(bracketed!=null) {
        	VariableType bracketedType = bracketed.semantischeAnalyse(tabelle, errors);
        	return bracketedType;
        } else { //token s
        	return VariableType.raT;
        }   
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        return null;
    }
}
