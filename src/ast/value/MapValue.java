package ast.value;

import ast.VariableType;

import java.util.Map;

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
