package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.value.SetValue;
import ast.value.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	@SuppressWarnings({"DuplicatedCode", "rawtypes"})
	@Override
	public SetValue run(SymbolTabelle tabelle) {
		List<Value> elementValues = elementList.stream()
				.map(element -> element.run(tabelle))
				.collect(Collectors.toList());

		VariableType type = elementValues.get(0).type;
		if(type.hasSameTypeAs(VariableType.booleanT)) {
			Set<Boolean> booleans = elementValues.stream()
					.map(element -> element.b)
					.collect(Collectors.toSet());
			return new SetValue<>(booleans, type);
		} else if(type.hasSameTypeAs(VariableType.stringT)) {
			Set<String> strings = elementValues.stream()
					.map(element -> element.string)
					.collect(Collectors.toSet());
			return new SetValue<>(strings, type);
		} else if (type.hasSameTypeAs(VariableType.charT)) {
			Set<Character> chars = elementValues.stream()
					.map(element -> element.c)
					.collect(Collectors.toSet());
			return new SetValue<>(chars, type);
		} else { // int
			Set<Integer> ints = elementValues.stream()
					.map(element -> element.i)
					.collect(Collectors.toSet());
			return new SetValue<>(ints, type);
		}
	}
}
