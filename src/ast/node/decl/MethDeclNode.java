package ast.node.decl;

import ast.CompilerError;
import ast.ParamWrapper;
import ast.SymbolTabelle;
import ast.VariableType;
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

	@SuppressWarnings("DuplicatedCode")
	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean addedError = false;

		VariableType bodyType = body.semantischeAnalyse(tabelle, errors);
		if (bodyType == VariableType.errorT) {
			return VariableType.errorT;
		}

		VariableType varType = type.semantischeAnalyse(tabelle, errors);
		if (varType.isError()) {
			return VariableType.errorT;
		}
		if (!varType.hasSameTypeAs(bodyType)) {
			errors.add(new CompilerError("Error: Type mismatch in line: " + identifier.beginLine));
			addedError = true;
		}

		String methodName = identifier.image;
		if (tabelle.findInCurrentBlock(methodName) != null) {
			errors.add(new CompilerError("Error: " + methodName + " already exists in line: "
					+ tabelle.find(methodName).identifier.beginLine));
			addedError = true;
		}

		// should always be false
		boolean paramTypeContainsError = params.values()
				.stream()
				.map(param -> param.getType().semantischeAnalyse(tabelle, errors))
				.anyMatch(type -> type == VariableType.errorT);
		if (paramTypeContainsError) {
			errors.add(new CompilerError("Error: " + methodName + " has an error in its paramter declerations in line: "
				+ tabelle.find(methodName).identifier.beginLine));
			addedError = true;
		}

		if (addedError) {
			return VariableType.errorT;
		} else {
			tabelle.add(methodName, this);
			return VariableType.noReturnType;
		}
	}
}
