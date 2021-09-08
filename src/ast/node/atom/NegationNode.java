package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;

import java.util.List;

public class NegationNode extends AtomNode {
	AtomNode node;

	public NegationNode(AtomNode node) {
		super();
		this.node = node;
	}

	public String toString(String indent) {
		return indent + "NegationNode\n" + node.toString(indent + "\t");
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType type = node.semantischeAnalyse(tabelle, errors);
		if (!type.hasSameTypeAs(VariableType.booleanT)) {
			errors.add(new CompilerError("Error: The operator ! can only be applied to a boolean but was applied to " + type));
			return VariableType.errorT;
		}
		return VariableType.booleanT;
	}
}
