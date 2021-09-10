package ast.node.atom.literals.regex;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.value.Value;

import java.util.LinkedList;
import java.util.List;

public class RegexConcatLitNode extends AtomNode {
    public List<RegexOptionLitNode> regexList = new LinkedList<>();

    @Override
    public String toString(String indent) {
        return null;
    }

    @Override
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
    	for(RegexOptionLitNode rol : regexList ) {
    		VariableType RegexOptionLitNodeType = rol.semantischeAnalyse(tabelle, errors);
    		if(!RegexOptionLitNodeType.hasSameTypeAs(VariableType.raT) || !RegexOptionLitNodeType.hasSameTypeAs(VariableType.rangeT)) {
    			errors.add(new CompilerError("Error: Operation Concat accepts only Range or regular express Type."));
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
