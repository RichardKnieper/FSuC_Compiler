package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.decl.DeclNode;
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
                if (method.equals("get")) {
                    if (methCallNode.elementList.size() == 1) {
                        return ((VariableType.SetVariableType) element.type.variableType).variableType;
                    } else {
                        errors.add(new CompilerError("Error: Wrong amount of parameters for method " + method
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
            } else if (type.isMapType()) {
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
}
