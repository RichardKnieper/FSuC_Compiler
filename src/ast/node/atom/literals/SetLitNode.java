package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
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

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		for (AtomNode node : elementList) {
			node.semantischeAnalyse(tabelle, errors);
		}
	}
}
