package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.value.Value;

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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType whileExprVariable = whileExpr.semantischeAnalyse(tabelle, errors);
		if (whileExprVariable == VariableType.errorT) {
			return VariableType.errorT;
		}

		if (whileExprVariable.hasSameTypeAs(VariableType.booleanT)) {
			whileStmnt.semantischeAnalyse(tabelle, errors);
			return VariableType.noReturnType;
		} else {
			errors.add(new CompilerError("Error: WHILE-condition must be boolean"));
			return VariableType.errorT;
		}
	}

	@SuppressWarnings("DuplicatedCode")
	@Override
	public Value run(SymbolTabelle tabelle) {
		while (whileExpr.run(tabelle).b) {
			whileStmnt.run(tabelle);
		}
		Value returnValue = new Value();
		returnValue.type = VariableType.noReturnType;
		return returnValue;
	}
}
