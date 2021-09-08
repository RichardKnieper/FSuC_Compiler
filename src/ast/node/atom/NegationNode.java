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

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		node.semantischeAnalyse(tabelle, errors);
		if (node.realType != VariableType.booleanT) {
			errors.add(new CompilerError(
					"Error: NegationNode, the operator ! is only defined for argument of type boolean"));
		}
	}
}
