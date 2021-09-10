package ast.node.stmnt;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.decl.DeclNode;
import ast.node.decl.MethDeclNode;
import ast.node.expr.ExprNode;
import ast.value.Value;
import jj.Token;

import java.util.List;

/**
 * Represents the (re-)assignment of a variable.
 */
public class AssignStmntNode extends StmntNode {
    Token identifier;
    ExprNode expr;

    public AssignStmntNode(Token identifier, ExprNode expr) {
        this.identifier = identifier;
        this.expr = expr;
    }

    @Override
    public String toString(String indent) {
        return null;
    }

    @Override
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        DeclNode variable = tabelle.find(identifier.image);
        VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
        if (variable == null) {
            errors.add(new CompilerError("Error: Variable by the name " + identifier.image + " does not exist "
                + "in line " + identifier.beginLine));
            return VariableType.errorT;
        }
        if (variable instanceof MethDeclNode) {
            errors.add(new CompilerError("Error: " + identifier.image + " is a method and not a variable "
                    + "in line " + identifier.beginLine));
            return VariableType.errorT;
        }

        if (exprType.hasSameTypeAs((variable.type.variableType))) {
            return VariableType.noReturnType;
        } else {
            errors.add(new CompilerError("Error: type mismatch in line " + identifier.beginLine));
            return VariableType.errorT;
        }
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        DeclNode variable = tabelle.find(identifier.image);
        variable.value = expr.run(tabelle);

        Value returnValue = new Value();
        returnValue.type = VariableType.noReturnType;
        return returnValue;
    }
}
