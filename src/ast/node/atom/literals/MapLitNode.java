package ast.node.atom.literals;

import ast.node.atom.AtomNode;

import java.util.HashMap;
import java.util.Map;

public class MapLitNode extends AtomNode {
	public Map<AtomNode, AtomNode> elements = new HashMap<>();

	public String toString(String indent) {
		String res = indent + "MapLitNode";
		for (Map.Entry<AtomNode, AtomNode> me : elements.entrySet()) {
			res = me.getKey().toString(indent + "\t") + me.getValue().toString(indent + "\t") + "\n";
		}
		return res;
	}
	/*
	 * public void semantischeAnalyse(ast.SymbolTabelle tabelle, List<ast.CompilerError>
	 * errors) { Iterator it = elements.entrySet().iterator(); Map.Entry<AtomNode,
	 * AtomNode> first = null; boolean temp = true; if(it.hasNext()) { first =
	 * (Map.Entry)it.next(); } while (it.hasNext()) { Map.Entry<AtomNode, AtomNode>
	 * second = (Map.Entry)it.next(); first.getKey().semantischeAnalyse(tabelle,
	 * errors); first.getValue().semantischeAnalyse(tabelle, errors);
	 * second.getKey().semantischeAnalyse(tabelle, errors);
	 * second.getValue().semantischeAnalyse(tabelle, errors); temp = temp &&
	 * VariableType.sameTypeAs()}; }
	 */
}
