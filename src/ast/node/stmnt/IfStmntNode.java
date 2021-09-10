package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.value.Value;

import java.util.List;

/**
 * Represents an if statement.
 * Semantic analysis checks for errors in all statements.
 * Interpreter only executes if or else statement depending on the value of the condition.
 */
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
		VariableType ifStmntType = ifStmnt.semantischeAnalyse(tabelle, errors);

		if (!ifExprVariable.isError() && !ifExprVariable.hasSameTypeAs(VariableType.booleanT)) {
			errors.add(new CompilerError("Error: IF-condition must be boolean"));
			return VariableType.errorT;
		}
		VariableType elseStmtType = null;
		if (elseStmnt != null) {
			elseStmtType = elseStmnt.semantischeAnalyse(tabelle, errors);
		}

		if (ifExprVariable.isError() || ifStmntType.isError() || (elseStmtType != null && elseStmtType.isError())) {
			return VariableType.errorT;
		} else {
			return VariableType.noReturnType;
		}
	}

	@SuppressWarnings("DuplicatedCode")
	@Override
	public Value run(SymbolTabelle tabelle) {
		Value ifExprValue = ifExpr.run(tabelle);
		if (ifExprValue.b) {
			ifStmnt.run(tabelle);
		} else {
			elseStmnt.run(tabelle);
		}
		Value returnValue = new Value();
		returnValue.type = VariableType.noReturnType;
		return returnValue;
	}
}
