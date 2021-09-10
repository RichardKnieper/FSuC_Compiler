package ast.node.decl;

import ast.CompilerError;
import ast.ParamWrapper;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.stmnt.StmntNode;
import ast.node.type.TypeNode;
import ast.value.Value;
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
		SymbolTabelle neueTabelle = new SymbolTabelle(tabelle);
		boolean addedError = false;

		String methodName = identifier.image;
		if (neueTabelle.findInCurrentBlock(methodName) != null) {
			errors.add(new CompilerError("Error: " + methodName + " already exists in line: "
					+ neueTabelle.find(methodName).identifier.beginLine + " and " + identifier.beginLine));
			addedError = true;
		}

		// should always be false
		boolean paramTypeContainsError = params.values()
				.stream()
				.map(param -> param.getType().semantischeAnalyse(neueTabelle, errors))
				.anyMatch(type -> type == VariableType.errorT);
		if (paramTypeContainsError) {
			errors.add(new CompilerError("Error: " + methodName + " has an error in its paramter declerations in line: "
					+ neueTabelle.find(methodName).identifier.beginLine));
			addedError = true;
		}

		boolean duplicateParamNames = params.values()
				.stream()
				.map(param -> param.getIndentifier().image)
				.distinct()
				.count() < params.size();
		if (duplicateParamNames) {
			errors.add(new CompilerError("Error: Parameter identifiers must be unique in line: "
					+ params.values().iterator().next().getIndentifier().beginLine));
			addedError = true;
		}

		params.values().forEach(paramWrapper ->
				neueTabelle.add(paramWrapper.getIndentifier().image, new DeclNode(paramWrapper.getType(), paramWrapper.getIndentifier())));

		VariableType bodyType = body.semantischeAnalyse(neueTabelle, errors);
		if (bodyType == VariableType.errorT) {
			return VariableType.errorT;
		}

		VariableType varType = type.semantischeAnalyse(neueTabelle, errors);
		if (varType.isError()) {
			return VariableType.errorT;
		}
		if (!varType.hasSameTypeAs(bodyType)) {
			errors.add(new CompilerError("Error: Type mismatch in line: " + identifier.beginLine));
			addedError = true;
		}

		if (addedError) {
			return VariableType.errorT;
		} else {
			tabelle.add(methodName, this);
			return VariableType.noReturnType;
		}
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		tabelle.add(identifier.image, this);
		return null;
	}
}
