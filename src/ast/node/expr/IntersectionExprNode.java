package ast.node.expr;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.value.Value;
import domain.BasicRange;
import domain.Range;

import java.util.List;

/**
 * Represents the intersection of two ranges.
 */
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
            Value firstValue = a.run(tabelle);
            Value secondValue = b.run(tabelle);
            Range first = firstValue.type.hasSameTypeAs(VariableType.rangeT) ? firstValue.r : tabelle.find(firstValue.identifier.image).value.r;
            Range second = secondValue.type.hasSameTypeAs(VariableType.rangeT) ? secondValue.r : tabelle.find(secondValue.identifier.image).value.r;

            Range range = null; // can not be initialized here because otherwise the add(...) method does not work

            for (BasicRange firstBR : first.getElement()) {
                for (BasicRange secondBR : second.getElement()) {
                    if (firstBR.isSingle()) {
                        if (firstBR.getStart() >= secondBR.getStart() && firstBR.getStart() <= secondBR.getEnd()) {
                            if (range == null) {
                                range = new Range(firstBR.getStart());
                            } else {
                                range.add(firstBR.getStart());
                            }
                        }
                    } else if (firstBR.getEnd() >= secondBR.getStart() && secondBR.getEnd() >= firstBR.getStart()) {
                        char start = (char) Math.max(firstBR.getStart(), secondBR.getStart());
                        char end = (char) Math.min(firstBR.getEnd(), secondBR.getEnd());
                        if (range == null) {
                            range = new Range(start, end);
                        } else {
                            range.add(start, end);
                        }
                    }
                }
            }
            return new Value(range);
        }
    }
}
