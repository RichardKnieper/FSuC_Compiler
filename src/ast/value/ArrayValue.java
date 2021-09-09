package ast.value;

import ast.VariableType;

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
