package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.Node;
import ast.value.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a block statement inside of curly brackets ('{' and '}').
 * The statement creates a new {@link SymbolTabelle} for the statements that it includes. This allows variables to
 * scope only to this block. It also allows the definition of a variable with an identifier that already exists without
 * overwriting the global variable or variable of a parent block statement.
 */
public class BlockStmntNode extends StmntNode {
	public List<Node> declOrStmntList = new LinkedList<>();

	public String toString(String indent) {
		String res = indent + "BlockStatement";
		for (Node node : declOrStmntList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		SymbolTabelle newSt = new SymbolTabelle(tabelle);
		List<VariableType> types = declOrStmntList.stream()
				.map(node -> node.semantischeAnalyse(newSt, errors))
				.collect(Collectors.toList());

		if (types.isEmpty()) {
			return VariableType.noReturnType;
		} else if (types.contains(VariableType.errorT)) {
			return VariableType.errorT;
		} else if (declOrStmntList.get(declOrStmntList.size() - 1) instanceof ReturnStmntNode) {
			return types.get(types.size() - 1);
		} else {
			return VariableType.noReturnType;
		}
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		SymbolTabelle neueTabelle = new SymbolTabelle(tabelle);
		List<Value> values = declOrStmntList.stream()
				.map(node -> node.run(neueTabelle))
				.collect(Collectors.toList());

		if (declOrStmntList.get(declOrStmntList.size() - 1) instanceof ReturnStmntNode) {
			return values.get(values.size() - 1);
		} else {
			Value returnValue = new Value();
			returnValue.type = VariableType.noReturnType;
			return returnValue;
		}
	}
}
