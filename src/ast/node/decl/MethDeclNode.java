package ast.node.decl;

import ast.CompilerError;
import ast.ParamWrapper;
import ast.SymbolTabelle;
import ast.node.stmnt.StmntNode;
import ast.node.type.TypeNode;
import jj.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethDeclNode extends DeclNode {
	public Map<Token, ParamWrapper> params = new HashMap<>();
	public StmntNode body = null;

	public MethDeclNode(TypeNode type, Token identifier) {
		super(type, identifier);
	}

	public String toString(String indent) {
		String res = indent + "CompilationUnit";
		for (Map.Entry<Token, ParamWrapper> me : params.entrySet()) {
			res = me.getValue().getType().toString(indent + "\t") + me.getKey().image + "\n";
		}
		return res;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		// type.semantischeAnalyse(tabelle, errors);
		if (!tabelle.add(identifier.image, this))
			errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
					+ tabelle.find(identifier.image).identifier.beginLine));
	}
}
