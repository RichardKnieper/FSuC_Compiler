package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
}
