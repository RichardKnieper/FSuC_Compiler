package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.value.Value;

import java.util.List;

public class IntersectionExprNode extends ExprNode {
    public PreOrPostIncrementExprNode a;
    public PreOrPostIncrementExprNode b;

    public IntersectionExprNode(PreOrPostIncrementExprNode a, PreOrPostIncrementExprNode b) {
        this.a = a;
        this.b = b;
    }

    public String toString(String indent) {
        return indent + "IntersectionExprNode " + "\n" + ((a != null) ? a.toString(indent + "\t") : "") + "\n"
                + ((b != null) ? b.toString(indent + "\t") : "");
    }

    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        VariableType aType = a.semantischeAnalyse(tabelle, errors);
        if (b != null) {
            VariableType bType = b.semantischeAnalyse(tabelle, errors);
            if (aType.hasSameTypeAs(bType)) {
                if (aType.hasSameTypeAs(VariableType.rangeT)) {
                    return VariableType.rangeT;
                } else {
                    errors.add(new CompilerError("Error: Intersection only accepts values of type Range."));
                    return VariableType.errorT;
                }
            } else {
                errors.add(new CompilerError("Error: Type mismatch for intersection."));
                return VariableType.errorT;
            }
        }
        return aType;
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        if (b == null) {
            return a.run(tabelle);
        } else {
            return a.run(tabelle); // TODO intersection
        }
    }
}
