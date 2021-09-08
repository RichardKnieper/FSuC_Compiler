package ast.node.atom;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.decl.DeclNode;
import jj.Token;

import java.util.List;

public class ReadClassAttributeNode extends AtomNode {
	Token classIdentifier, attributeIdentifier;

	public ReadClassAttributeNode(Token classIdentifier, Token attributeIdentifier) {
		this.classIdentifier = classIdentifier;
		this.attributeIdentifier = attributeIdentifier;
	}

	public String toString(String indent) {
		return indent + "ReadClassAttributeNode: " + classIdentifier + "." + attributeIdentifier + "\n";
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		DeclNode element = tabelle.find(classIdentifier.image);
		if (element == null) {
			errors.add(new CompilerError("Error: " + classIdentifier.image + " is not defined in line: "+
					classIdentifier.beginLine));
		} else {
			VariableType type = element.type.variableType;
			String attribute = attributeIdentifier.image;
			if (type.hasSameTypeAs(VariableType.faT)) {
				if (attribute.equals("start")) {
					return VariableType.stateT;
				} else if (attribute.equals("states")) {
					return new VariableType.SetVariableType(VariableType.stateT);
				} else {
					errors.add(new CompilerError("Error: FA does not have attribute: " + attribute));
				}
			} else if (type.hasSameTypeAs(VariableType.transitionT)) {

				if (attribute.equals("source") || attribute.equals("target")) {
					return VariableType.stateT;
				} else if (attribute.equals("range")) {
					return VariableType.rangeT;
				} else {
					errors.add(new CompilerError("Error: Transition does not have attribute: " + attribute));
				}
			} else if (type.hasSameTypeAs(VariableType.stateT)) {
				if (attribute.equals("label")) {
					return VariableType.stateT;
				} else if (attribute.equals("accept")) {
					return VariableType.intT;
				} else {
					errors.add(new CompilerError("Error: State does not have attribute: " + attribute));
				}
			} else {
				errors.add(new CompilerError("Error: can not read attribute from class/datatype: " + type));
			}
		}

		return VariableType.errorT;
	}
}
