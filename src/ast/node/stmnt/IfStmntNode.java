package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;

import java.util.List;

public class IfStmntNode extends StmntNode {
	public StmntNode ifExpr, ifStmnt, elseStmnt;

	public IfStmntNode(StmntNode ifExpr, StmntNode ifStmnt, StmntNode elseStmnt) {
		this.ifExpr = ifExpr;
		this.ifStmnt = ifStmnt;
		this.elseStmnt = elseStmnt;
	}

	public String toString(String indent) {
		return indent + "IfStatement\n" + ifExpr.toString(indent + "\t") + "\n" + ifStmnt.toString(indent + "\t")
				+ (elseStmnt != null ? "\n" + elseStmnt.toString(indent + "\t") : "");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		ifExpr.semantischeAnalyse(tabelle, errors);
		if (ifExpr.realType != VariableType.booleanT && ifExpr.realType != VariableType.errorT) {
			errors.add(new CompilerError("Error: IF-condition must be boolean"));
		}
		ifStmnt.semantischeAnalyse(tabelle, errors);
		if (elseStmnt != null)
			elseStmnt.semantischeAnalyse(tabelle, errors);
	}
}
