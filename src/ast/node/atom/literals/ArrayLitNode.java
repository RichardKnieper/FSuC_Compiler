package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.value.ArrayValue;
import ast.value.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the creation of an array.
 */
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

		return new VariableType.ArrayVariableType(type);
	}

	@SuppressWarnings({"DuplicatedCode", "rawtypes"})
	@Override
	public ArrayValue run(SymbolTabelle tabelle) {
		List<Value> elementValues = elementList.stream()
				.map(element -> element.run(tabelle))
				.collect(Collectors.toList());

		VariableType type = elementValues.get(0).type;
		if(type.hasSameTypeAs(VariableType.booleanT)) {
			Boolean[] booleans = (Boolean[]) elementValues.stream()
					.map(element -> element.b)
					.toArray();
			return new ArrayValue<>(booleans, type);
		} else if(type.hasSameTypeAs(VariableType.stringT)) {
			String[] strings = (String[]) elementValues.stream()
					.map(element -> element.string)
					.toArray();
			return new ArrayValue<>(strings, type);
		} else if (type.hasSameTypeAs(VariableType.charT)) {
			Character[] chars = (Character[]) elementValues.stream()
					.map(element -> element.c)
					.toArray();
			return new ArrayValue<>(chars, type);
		} else { // int
			Integer[] ints = (Integer[]) elementValues.stream()
					.map(element -> element.i)
					.toArray();
			return new ArrayValue<>(ints, type);
		}
	}
}
