package ast.node.decl;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.expr.ExprNode;
import ast.node.type.TypeNode;
import jj.Token;

import java.util.List;

public class VarDeclNode extends DeclNode {
	public ExprNode expr;

	public VarDeclNode(TypeNode type, Token identifier, ExprNode expr) {
		super(type, identifier);
		this.expr = expr;
	}

	public String toString(String indent) {
		return indent + "VarDeclNode\n" + expr.toString(indent + "\t") + "\n";
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {

		expr.semantischeAnalyse(tabelle, errors);
		type.semantischeAnalyse(tabelle, errors);

		if (VariableType.sameTypeAs(type.variableType, expr.realType)) {
			if (!tabelle.add(identifier.image, this))
				errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
						+ tabelle.find(identifier.image).identifier.beginLine));
		} else {
			errors.add(new CompilerError("Error: Type mismatch in line: " + identifier.beginLine));
		}
	}
}
