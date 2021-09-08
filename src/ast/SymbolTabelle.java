package ast;

import ast.node.decl.DeclNode;

import java.util.HashMap;

public class SymbolTabelle {

	SymbolTabelle parent;
	HashMap<String, DeclNode> table = new HashMap<>();

	public SymbolTabelle(SymbolTabelle p) {
		parent = p;
	}

	public DeclNode find(String n) {
		if (table.containsKey(n))
			return table.get(n);
		else if (parent != null)
			return parent.find(n);
		else
			return null;
	}

	public boolean add(String n, DeclNode node) {
		DeclNode f = find(n);
		if (f != null) {
			return false;
		} else {
			table.put(n, node);
			return true;
		}
	}

}
