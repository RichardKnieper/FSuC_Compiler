package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import jj.Token;

import java.util.List;

public class ClassMethCallNode extends MethCallNode {
	public Token objectIdentifier;
	public MethCallNode methCallNode;

	public ClassMethCallNode(Token objectIdentifier, MethCallNode methCallNode) {
		this.objectIdentifier = objectIdentifier;
		this.methCallNode = methCallNode;
	}

	public String toString(String indent) {
		return indent + "ClassMethCallNode:\n" + ((objectIdentifier != null) ? objectIdentifier.image : "")
				+ methCallNode.toString(indent + "\t") + "\n";
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (tabelle.find(objectIdentifier.image) != null) {
			methCallNode.semantischeAnalyse(tabelle, errors);
		} else {
			errors.add(new CompilerError("Error: Class " + objectIdentifier.image + " does not exist"));
		}
	}
}
