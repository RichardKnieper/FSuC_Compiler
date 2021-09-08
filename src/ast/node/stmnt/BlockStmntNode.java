package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.Node;

import java.util.LinkedList;
import java.util.List;

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
		for (Node node : declOrStmntList) {
			node.semantischeAnalyse(newSt, errors);
		}
		return VariableType.noReturnType;
	}
}
