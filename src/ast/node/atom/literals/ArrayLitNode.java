package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;

import java.util.LinkedList;
import java.util.List;

public class ArrayLitNode extends AtomNode {
	public List<AtomNode> elementList = new LinkedList<>();

	public String toString(String indent) {
		String res = indent + "ArrayLitNode";
		for (AtomNode node : elementList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType type = elementList.stream()
				.map(atom -> atom.semantischeAnalyse(tabelle, errors))
				.reduce((a, b) -> a.hasSameTypeAs(b) ? a : VariableType.errorT)
				.get();

		if (type.isError()) {
			errors.add(new CompilerError("Error: not all values of array match."));
		}

		return type;
	}
}
