package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import jj.Token;

import java.util.LinkedList;
import java.util.List;

public class MethCallNode extends AtomNode {
	public Token identifier;
	public List<AtomNode> elementList = new LinkedList<>();

	public MethCallNode() {
	}

	public String toString(String indent) {
		String res = indent + "MethCallNode" + identifier.image + "\n";
		for (AtomNode node : elementList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (tabelle.find(identifier.image) != null) {
			for (AtomNode node : elementList) {
				node.semantischeAnalyse(tabelle, errors);
			}
		} else {
			errors.add(new CompilerError("Error: Class " + identifier.image + " does not exist"));
		}
	}
}
