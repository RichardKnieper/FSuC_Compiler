package ast.node.atom;

import ast.SymbolTabelle;
import ast.VariableType;
import ast.exceptions.CompilerError;
import ast.node.decl.DeclNode;
import ast.value.ArrayValue;
import ast.value.MapValue;
import ast.value.SetValue;
import ast.value.Value;
import jj.Token;

import java.util.List;

public class ClassMethCallNode extends AtomNode {
    public Token objectIdentifier;
    public MethCallNode methCallNode;

    public ClassMethCallNode(Token objectIdentifier, MethCallNode methCallNode) {
        this.objectIdentifier = objectIdentifier;
        this.methCallNode = methCallNode;
    }

    public String toString(String indent) {
        return indent + "ClassMethCallNode:\n" + ((objectIdentifier != null) ? objectIdentifier.image : "")
                + methCallNode.toString(indent + "\t") + "\n";
    }

    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        DeclNode element = tabelle.find(objectIdentifier.image);
        if (element == null) {
            errors.add(new CompilerError("Error: " + objectIdentifier.image + " is not defined in line: " +
                    objectIdentifier.beginLine));
        } else {
            VariableType type = element.type.variableType;
            String method = methCallNode.identifier.image;
            if (type.hasSameTypeAs(VariableType.stringT)) {
                if (method.equals("length")) {
                    if (methCallNode.elementList.isEmpty()) {
                        return VariableType.intT;
                    } else {
                        errors.add(new CompilerError("Error: Too many parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                } else if (method.equals("charAt")) {
                    if (methCallNode.elementList.size() == 1) {
                        return VariableType.charT;
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                }
            } else if (type.hasSameTypeAs(VariableType.rangeT)) {
                if (method.contains("isEmpty")) {
                    if (methCallNode.elementList.isEmpty()) {
                        return VariableType.booleanT;
                    } else {
                        errors.add(new CompilerError("Error: Too many parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                } else if (method.equals("contains")) {
                    if (methCallNode.elementList.size() == 1) {
                        return VariableType.booleanT;
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                }
            } else if (type.hasSameTypeAs(VariableType.faT)) {
                if (method.contains("transitions")) {
                    if (methCallNode.elementList.size() == 1) {
                        return new VariableType.SetVariableType(VariableType.transitionT);
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                }
            } else if (type.isArrayType()) {
                if (method.equals("get")) {
                    if (methCallNode.elementList.size() == 1) {
                        return ((VariableType.ArrayVariableType) element.type.variableType).variableType;
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                }
            } else if (type.isSetType()) {
                // TODO contains check type
                if (method.equals("contains")) {
                    if (methCallNode.elementList.size() == 1) {
                        return VariableType.booleanT;
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                }
            } else if (type.isMapType()) {
                // TODO check key type
                if (method.equals("get")) {
                    if (methCallNode.elementList.size() == 1) {
                        return ((VariableType.MapVariableType) element.type.variableType).valueVariableType;
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                } else if (method.equals("containsKey")) {
                    if (methCallNode.elementList.size() == 1) {
                        return VariableType.booleanT;
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
                                + " on class String in line: " + methCallNode.identifier.beginLine));
                    }
                }
            } else {
                errors.add(new CompilerError("Error: can not call method " + method + " on class " + type
                        + " in line " + objectIdentifier.beginLine));
            }
        }

        return VariableType.errorT;
    }

    @SuppressWarnings({"DuplicatedCode", "rawtypes"})
    @Override
    public Value run(SymbolTabelle tabelle) {
        Value element = tabelle.find(objectIdentifier.image).value;

        VariableType type = element.type;
        String method = methCallNode.identifier.image;

        if (type.hasSameTypeAs(VariableType.stringT)) {
            if (method.equals("length")) {
                return new Value(element.string.length());
            } else { // charAt(i)
                int i = methCallNode.elementList.get(0).run(tabelle).i;
                try {
                    return new Value(element.string.charAt(i));
                } catch (IndexOutOfBoundsException e) {
                    throw new RuntimeException("IndexOutOfBoundsException: " + i);
                }
            }
        } else if (type.hasSameTypeAs(VariableType.rangeT)) {
            if (method.contains("isEmpty")) {
                return new Value(element.r.isEmpty());
            } else if (method.equals("contains")) {
                // TODO implement
                throw new RuntimeException("Methode nicht implementiert");
            }
        } else if (type.hasSameTypeAs(VariableType.faT)) {
            if (method.contains("transitions")) {
                // TODO implement
                throw new RuntimeException("Methode nicht implementiert");
            }
        } else if (type.isArrayType()) {
            if (method.equals("get")) {
                int i = methCallNode.elementList.get(0).run(tabelle).i;
                try {
                    Object arrayValue = ((ArrayValue) element).get(i);
                    if (((VariableType.ArrayVariableType) type).variableType.hasSameTypeAs(VariableType.booleanT)) {
                        return new Value((Boolean) arrayValue);
                    } else if (((VariableType.ArrayVariableType) type).variableType.hasSameTypeAs(VariableType.intT)) {
                        return new Value((Integer) arrayValue);
                    } else if (((VariableType.ArrayVariableType) type).variableType.hasSameTypeAs(VariableType.charT)) {
                        return new Value((Character) arrayValue);
                    } else { // String
                        return new Value((String) arrayValue);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new RuntimeException("ArrayIndexOutOfBoundsException: " + i);
                }
            }
        } else if (type.isSetType()) {
            // contains
            Value p = methCallNode.elementList.get(0).run(tabelle);
            if (((VariableType.SetVariableType) type).variableType.hasSameTypeAs(VariableType.booleanT)) {
                return new Value(((SetValue) element).contains(p.b));
            } else if (((VariableType.SetVariableType) type).variableType.hasSameTypeAs(VariableType.intT)) {
                return new Value(((SetValue) element).contains(p.i));
            } else if (((VariableType.SetVariableType) type).variableType.hasSameTypeAs(VariableType.charT)) {
                return new Value(((SetValue) element).contains(p.c));
            } else { // String
                return new Value(((SetValue) element).contains(p.string));
            }
        } else if (type.isMapType()) {
            if (method.equals("get")) {
                Value p = methCallNode.elementList.get(0).run(tabelle);
                if (((VariableType.MapVariableType) type).keyVariableType.hasSameTypeAs(VariableType.booleanT)) {
                    if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.booleanT)) {
                        return new Value((Boolean) ((MapValue) element).get(p.b));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.intT)) {
                        return new Value((Integer) ((MapValue) element).get(p.b));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.charT)) {
                        return new Value((Character) ((MapValue) element).get(p.b));
                    } else { // String
                        return new Value((String) ((MapValue) element).get(p.b));
                    }
                } else if (((VariableType.MapVariableType) type).keyVariableType.hasSameTypeAs(VariableType.intT)) {
                    if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.booleanT)) {
                        return new Value((Boolean) ((MapValue) element).get(p.i));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.intT)) {
                        return new Value((Integer) ((MapValue) element).get(p.i));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.charT)) {
                        return new Value((Character) ((MapValue) element).get(p.i));
                    } else { // String
                        return new Value((String) ((MapValue) element).get(p.i));
                    }
                } else if (((VariableType.MapVariableType) type).keyVariableType.hasSameTypeAs(VariableType.charT)) {
                    if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.booleanT)) {
                        return new Value((Boolean) ((MapValue) element).get(p.c));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.intT)) {
                        return new Value((Integer) ((MapValue) element).get(p.c));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.charT)) {
                        return new Value((Character) ((MapValue) element).get(p.c));
                    } else { // String
                        return new Value((String) ((MapValue) element).get(p.c));
                    }
                } else { // String
                    if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.booleanT)) {
                        return new Value((Boolean) ((MapValue) element).get(p.string));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.intT)) {
                        return new Value((Integer) ((MapValue) element).get(p.string));
                    } else if (((VariableType.MapVariableType) type).valueVariableType.hasSameTypeAs(VariableType.charT)) {
                        return new Value((Character) ((MapValue) element).get(p.string));
                    } else { // String
                        return new Value((String) ((MapValue) element).get(p.string));
                    }
                }
            } else if (method.equals("containsKey")) {
                Value p = methCallNode.elementList.get(0).run(tabelle);
                if (((VariableType.MapVariableType) type).keyVariableType.hasSameTypeAs(VariableType.booleanT)) {
                    return new Value(((MapValue) element).containsKey(p.b));
                } else if (((VariableType.MapVariableType) type).keyVariableType.hasSameTypeAs(VariableType.intT)) {
                    return new Value(((MapValue) element).containsKey(p.i));
                } else if (((VariableType.MapVariableType) type).keyVariableType.hasSameTypeAs(VariableType.charT)) {
                    return new Value(((MapValue) element).containsKey(p.c));
                } else { // String
                    return new Value(((MapValue) element).containsKey(p.string));
                }
            }
        }
        return null; // should never be reached TODO refactor
    }
}
