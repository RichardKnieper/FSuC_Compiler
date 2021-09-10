package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.value.MapValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapLitNode extends AtomNode {
    public Map<AtomNode, AtomNode> elements = new HashMap<>();
    public VariableType keyType, valueType;

    public String toString(String indent) {
        String res = indent + "MapLitNode";
        for (Map.Entry<AtomNode, AtomNode> me : elements.entrySet()) {
            res = me.getKey().toString(indent + "\t") + me.getValue().toString(indent + "\t") + "\n";
        }
        return res;
    }

    @Override
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        VariableType keyTypes = elements.keySet()
                .stream()
                .map(atom -> atom.semantischeAnalyse(tabelle, errors))
                .reduce((a, b) -> a.hasSameTypeAs(b) ? a : VariableType.errorT)
                .get();

        VariableType valueTypes = elements.values()
                .stream()
                .map(atom -> atom.semantischeAnalyse(tabelle, errors))
                .reduce((a, b) -> a.hasSameTypeAs(b) ? a : VariableType.errorT)
                .get();

        if (keyTypes.isError()) {
            errors.add(new CompilerError("Error: not all key types of map match."));
        }

        if (valueTypes.isError()) {
            errors.add(new CompilerError("Error: not all value types of map match."));
        }

        if (keyTypes.isError() || valueTypes.isError()) {
            return VariableType.errorT;
        } else {
            this.keyType = keyTypes;
            this.valueType = valueTypes;
            return new VariableType.MapVariableType(keyTypes, valueTypes);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public MapValue run(SymbolTabelle tabelle) {
        if(keyType.hasSameTypeAs(VariableType.booleanT)) {

            if(valueType.hasSameTypeAs(VariableType.booleanT)) { // boolean -> boolean
                Map<Boolean, Boolean> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            boolean key = keyNode.run(tabelle).b;
                            boolean value = elements.get(keyNode).run(tabelle).b;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if(valueType.hasSameTypeAs(VariableType.stringT)) { // boolean -> String
                Map<Boolean, String> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            boolean key = keyNode.run(tabelle).b;
                            String value = elements.get(keyNode).run(tabelle).string;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if (valueType.hasSameTypeAs(VariableType.charT)) { // boolean -> char
                Map<Boolean, Character> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            boolean key = keyNode.run(tabelle).b;
                            char value = elements.get(keyNode).run(tabelle).c;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else { // boolean -> int
                Map<Boolean, Integer> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            boolean key = keyNode.run(tabelle).b;
                            int value = elements.get(keyNode).run(tabelle).i;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);
            }

        } else if(keyType.hasSameTypeAs(VariableType.stringT)) {

            if(valueType.hasSameTypeAs(VariableType.booleanT)) { // String -> boolean
                Map<String, Boolean> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            String key = keyNode.run(tabelle).string;
                            boolean value = elements.get(keyNode).run(tabelle).b;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if(valueType.hasSameTypeAs(VariableType.stringT)) { // String -> String
                Map<String, String> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            String key = keyNode.run(tabelle).string;
                            String value = elements.get(keyNode).run(tabelle).string;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if (valueType.hasSameTypeAs(VariableType.charT)) { // String -> char
                Map<String, Character> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            String key = keyNode.run(tabelle).string;
                            char value = elements.get(keyNode).run(tabelle).c;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else { // String -> int
                Map<String, Integer> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            String key = keyNode.run(tabelle).string;
                            int value = elements.get(keyNode).run(tabelle).i;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);
            }

        } else if (keyType.hasSameTypeAs(VariableType.charT)) {

            if(valueType.hasSameTypeAs(VariableType.booleanT)) {  // char -> boolean
                Map<Character, Boolean> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            char key = keyNode.run(tabelle).c;
                            boolean value = elements.get(keyNode).run(tabelle).b;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if(valueType.hasSameTypeAs(VariableType.stringT)) {  // char -> String
                Map<Character, String> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            char key = keyNode.run(tabelle).c;
                            String value = elements.get(keyNode).run(tabelle).string;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if (valueType.hasSameTypeAs(VariableType.charT)) { // char -> char
                Map<Character, Character> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            char key = keyNode.run(tabelle).c;
                            char value = elements.get(keyNode).run(tabelle).c;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else { // char -> int
                Map<Character, Integer> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            char key = keyNode.run(tabelle).c;
                            int value = elements.get(keyNode).run(tabelle).i;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);
            }

        } else {

            if(valueType.hasSameTypeAs(VariableType.booleanT)) { // int -> boolean
                Map<Integer, Boolean> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            int key = keyNode.run(tabelle).i;
                            boolean value = elements.get(keyNode).run(tabelle).b;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if(valueType.hasSameTypeAs(VariableType.stringT)) { // int -> String
                Map<Integer, String> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            int key = keyNode.run(tabelle).i;
                            String value = elements.get(keyNode).run(tabelle).string;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else if (valueType.hasSameTypeAs(VariableType.charT)) { // int -> char
                Map<Integer, Character> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            int key = keyNode.run(tabelle).i;
                            char value = elements.get(keyNode).run(tabelle).c;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);

            } else { // int -> int
                Map<Integer, Integer> map = new HashMap<>();
                elements.keySet()
                        .forEach(keyNode -> {
                            int key = keyNode.run(tabelle).i;
                            int value = elements.get(keyNode).run(tabelle).i;
                            map.put(key, value);
                        });
                return new MapValue<>(map, keyType, valueType);
            }
        }
    }
}
