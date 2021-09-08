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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType ifExprVariable = ifExpr.semantischeAnalyse(tabelle, errors);
		if (ifExprVariable == VariableType.errorT) {
            return VariableType.errorT;
        }
		
		if(ifExprVariable.hasSameTypeAs(VariableType.booleanT)) {
			ifStmnt.semantischeAnalyse(tabelle, errors);
			if (elseStmnt != null)
				elseStmnt.semantischeAnalyse(tabelle, errors);
			return VariableType.noReturnType;
		}
		else {
			errors.add(new CompilerError("Error: IF-condition must be boolean"));
			return VariableType.errorT;
		}	
	}
}
