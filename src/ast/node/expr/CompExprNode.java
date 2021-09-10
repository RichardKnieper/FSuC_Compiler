package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.value.Value;
import jj.Token;

import java.util.List;

/**
 * Represents comparison of values with '!=', '==', '>', '<', '>=' and '<=' operators.
 */
@SuppressWarnings("DuplicatedCode")
public class CompExprNode extends ExprNode {
    public SumExprNode expr;
    public Token op;
    public SumExprNode secondExpr;

    public CompExprNode(SumExprNode expr, Token op, SumExprNode secondExpr) {
        this.expr = expr;
        this.op = op;
        this.secondExpr = secondExpr;
    }

    public String toString(String indent) {
        return indent + "CompExprNode " + ((op != null) ? op.image : "") + "\n"
                + ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
                + ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
    }

    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        VariableType exprType = expr.semantischeAnalyse(tabelle, errors);
        if (secondExpr != null) {
            VariableType secondExprType = secondExpr.semantischeAnalyse(tabelle, errors);
            if (secondExprType.hasSameTypeAs(exprType)) {
                if (op.image.equals("!=") || op.image.equals("==")) {
                    if ((secondExprType.hasSameTypeAs(VariableType.booleanT))
                            || (secondExprType.hasSameTypeAs(VariableType.intT))
                            || (secondExprType.hasSameTypeAs(VariableType.charT))
                            || (secondExprType.hasSameTypeAs(VariableType.stringT))) {
                        return VariableType.booleanT;
                    } else {
                        errors.add(new CompilerError("Error: Comparison in line " + op.beginLine + ", " + op.image
                                + " accepts only Boolean, Character or Integer."));
                        return VariableType.errorT;
                    }
                } else {
                    if (secondExprType.hasSameTypeAs(VariableType.intT) || secondExprType.hasSameTypeAs(VariableType.charT)) {
                        return VariableType.booleanT;
                    } else {
                        errors.add(new CompilerError(
                                "Error: Comparison in line " + op.beginLine + ", " + op.image + " accepts only Integer or Character."));
                        return VariableType.errorT;
                    }
                }
            } else {
                errors.add(new CompilerError("Error: Type mismatch for operator " + op.image + " in line " + op.beginLine));
                return VariableType.errorT;
            }
        }
        return exprType;
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        if (op == null) {
            return expr.run(tabelle);
        } else {
            Value firstValue = expr.run(tabelle);
            Value secondValue = secondExpr.run(tabelle);

            if (firstValue.type.hasSameTypeAs(VariableType.identifier)) {
                firstValue = tabelle.find(firstValue.identifier.image).value;
            }
            if (secondValue.type.hasSameTypeAs(VariableType.identifier)) {
                secondValue = tabelle.find(secondValue.identifier.image).value;
            }

            switch (op.image) {
                case "==":
                    if (firstValue.type.hasSameTypeAs(VariableType.booleanT)) {
                        return new Value(firstValue.b == secondValue.b);
                    } else if (firstValue.type.hasSameTypeAs(VariableType.charT)) {
                        return new Value(firstValue.c == secondValue.c);
                    } else if (firstValue.type.hasSameTypeAs(VariableType.intT)) {
                        return new Value(firstValue.i == secondValue.i);
                    } else {
                        return new Value(firstValue.string.equals(secondValue.string));
                    }
                case "!=":
                    if (firstValue.type.hasSameTypeAs(VariableType.booleanT)) {
                        return new Value(firstValue.b != secondValue.b);
                    } else if (firstValue.type.hasSameTypeAs(VariableType.charT)) {
                        return new Value(firstValue.c != secondValue.c);
                    } else if (firstValue.type.hasSameTypeAs(VariableType.intT)) {
                        return new Value(firstValue.i != secondValue.i);
                    } else {
                        return new Value(!firstValue.string.equals(secondValue.string));
                    }
                case "<=":
                    if (firstValue.type.hasSameTypeAs(VariableType.intT)) {
                        return new Value(firstValue.i <= secondValue.i);
                    } else { // char
                        return new Value(firstValue.c <= secondValue.c);
                    }
                case ">=":
                    if (firstValue.type.hasSameTypeAs(VariableType.intT)) {
                        return new Value(firstValue.i >= secondValue.i);
                    } else { // char
                        return new Value(firstValue.c >= secondValue.c);
                    }
                case "<":
                    if (firstValue.type.hasSameTypeAs(VariableType.intT)) {
                        return new Value(firstValue.i < secondValue.i);
                    } else { // char
                        return new Value(firstValue.c < secondValue.c);
                    }
                default:  // op = ">"
                    if (firstValue.type.hasSameTypeAs(VariableType.intT)) {
                        return new Value(firstValue.i > secondValue.i);
                    } else { // char
                        return new Value(firstValue.c > secondValue.c);
                    }
            }
        }
    }
}
