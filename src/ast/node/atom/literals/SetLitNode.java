package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;

import java.util.LinkedList;
import java.util.List;

public class SetLitNode extends AtomNode {
	public List<AtomNode> elementList = new LinkedList<>();

	public String toString(String indent) {
		String res = indent + "SetLitNode";
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
			errors.add(new CompilerError("Error: not all values of set match."));
		}

		return new VariableType.SetVariableType(type);
	}
}
