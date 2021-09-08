package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;

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

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean temp = true;
		for (int i = 0; i < elementList.size() - 1; i++) {
			AtomNode first = elementList.get(i);
			AtomNode second = elementList.get(i + 1);
			first.semantischeAnalyse(tabelle, errors);
			second.semantischeAnalyse(tabelle, errors);
			temp = temp && VariableType.sameTypeAs(first.realType, (second.realType));
		}
		if (temp) {
			realType = elementList.get(1).realType;
		} else {
			errors.add(new CompilerError("Error: The elements in an array must be of the same type."));
			realType = VariableType.errorT;
		}
	}
}
