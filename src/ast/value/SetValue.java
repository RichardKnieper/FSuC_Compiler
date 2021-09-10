package ast.value;

import ast.VariableType;

import java.util.Set;

/**
 * Represents the return value of a statement/expression when a set is returned. Used when interpreting the AST.
 * @param <T> Type of the values that are stored.
 */
public class SetValue<T> extends Value {
    Set<T> set;

    public SetValue(Set<T> set, VariableType type) {
        this.set = set;
        this.type = new VariableType.SetVariableType(type);
    }

    public boolean contains(T i) {
        return set.contains(i);
    }
}
