package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;

import java.util.List;

public class WhileStmntNode extends StmntNode {
	public StmntNode whileExpr, whileStmnt;

	public WhileStmntNode(StmntNode whileExpr, StmntNode whileStmnt) {
		this.whileExpr = whileExpr;
		this.whileStmnt = whileStmnt;
	}

	public String toString(String indent) {
		return indent + "WhileStatement\n" + whileExpr.toString(indent + "\t") + "\n"
				+ whileStmnt.toString(indent + "\t");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		whileExpr.semantischeAnalyse(tabelle, errors);
		if (whileExpr.realType != VariableType.booleanT && whileExpr.realType != VariableType.errorT) {
			errors.add(new CompilerError("Error: WHILE-condition must be boolean"));
		}
		whileStmnt.semantischeAnalyse(tabelle, errors);
	}
}
