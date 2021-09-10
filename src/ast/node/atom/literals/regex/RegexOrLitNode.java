package ast.node.atom.literals.regex;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.value.Value;

import java.util.LinkedList;
import java.util.List;

public class RegexOrLitNode extends AtomNode {
    public List<RegexConcatLitNode> regexList = new LinkedList<>();

    @Override
    public String toString(String indent) {
        return null;
    }

    @Override
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
    	for(RegexConcatLitNode rcl : regexList ) {
    		VariableType RegexConcatLitNodeType = rcl.semantischeAnalyse(tabelle, errors);
    		if(!RegexConcatLitNodeType.hasSameTypeAs(VariableType.raT) 
    				|| !RegexConcatLitNodeType.hasSameTypeAs(VariableType.rangeT)
    				|| RegexConcatLitNodeType.hasSameTypeAs(VariableType.errorT)) {
    			errors.add(new CompilerError("Error: Concat accepts only Range or regular express Type."));
    			return VariableType.errorT;
    		}
    	}
    	return VariableType.raT;
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        return null;
    }
}
