package ast.value;

import ast.VariableType;

/**
 * Represents the return value of a statement/expression when an array is returned. Used when interpreting the AST.
 * @param <T> Type of the values that are stored.
 */
public class ArrayValue<T> extends Value {
    T[] array;

    public ArrayValue(T[] array, VariableType type) {
        this.array = array;
        this.type = new VariableType.ArrayVariableType(type);
    }

    public T get(int i) {
        return array[i];
    }
}
