package ast.value;

import ast.VariableType;

import java.util.Map;

/**
 * Represents the return value of a statement/expression when a map is returned. Used when interpreting the AST.
 * @param <K> Type of the keys .
 * @param <V> Type of the values.
 */
public class MapValue<K, V> extends Value {
    Map<K, V> map;

    public MapValue(Map<K, V> map, VariableType keyType, VariableType valueType) {
        this.map = map;
        this.type = new VariableType.MapVariableType(keyType, valueType);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public V get(K key) {
        return map.get(key);
    }
}
