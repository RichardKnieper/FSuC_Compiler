package ast.node.decl;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.expr.ExprNode;
import ast.node.type.TypeNode;
import ast.value.Value;
import jj.Token;

import java.util.List;

/**
 * Represents declaration and initialization of variable.
 */
public class VarDeclNode extends DeclNode {
    public ExprNode expr;

    public VarDeclNode(TypeNode type, Token identifier, ExprNode expr) {
        super(type, identifier);
        this.expr = expr;
    }

    public String toString(String indent) {
        return indent + "VarDeclNode\n" + expr.toString(indent + "\t") + "\n";
    }

    @SuppressWarnings("DuplicatedCode")
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        boolean addedError = false;

        VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
        if (exprType == VariableType.errorT) {
            return VariableType.errorT;
        }

        VariableType varType = type.semantischeAnalyse(tabelle, errors);
        if (varType.isError()) {
            return VariableType.errorT;
        }
        if (!varType.hasSameTypeAs(exprType)) {
            errors.add(new CompilerError("Error: Type mismatch in line: " + identifier.beginLine));
            addedError = true;
        }

        String varName = identifier.image;
        if (tabelle.findInCurrentBlock(varName) != null) {
            errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
                    + tabelle.find(identifier.image).identifier.beginLine + " and " + identifier.beginLine));
            addedError = true;
        }

        if (addedError) {
            return VariableType.errorT;
        } else {
            tabelle.add(varName, this);
            return VariableType.noReturnType;
        }
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        this.value = expr.run(tabelle);
        tabelle.add(identifier.image, this);
        return null;
    }
}
