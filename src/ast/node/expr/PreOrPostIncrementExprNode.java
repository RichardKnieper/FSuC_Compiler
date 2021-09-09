package ast.node.expr;

import ast.SymbolTabelle;
import ast.VariableType;
import ast.exceptions.CompilerError;
import ast.node.atom.AtomNode;
import ast.value.Value;
import jj.Token;

import java.util.List;

public class PreOrPostIncrementExprNode extends ExprNode {
	public AtomNode atom;
	public Token pre;
	public Token post;

	public PreOrPostIncrementExprNode(AtomNode atom, Token pre, Token post) {
		this.atom = atom;
		this.pre = pre;
		this.post = post;
	}

	public String toString(String indent) {
		return indent + "IntersectionExprNode " + "\n" + atom.toString(indent + "\t") + "\n"
				+ ((pre != null) ? pre.image : "") + ((post != null) ? post.image : "");
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		VariableType atomType = atom.semantischeAnalyse(tabelle, errors);
		if (post != null) {
			if (atomType.hasSameTypeAs(VariableType.intT))
				return VariableType.intT;
			else {
				errors.add(new CompilerError("Error: Postoperation " + post.image + " in line " + post.beginLine
						+ " needs to be a value of type Integer."));
				return VariableType.errorT;
			}
		}
		if (pre != null) {
			if (atomType.hasSameTypeAs(VariableType.intT))
				return VariableType.intT;
			else {
				errors.add(new CompilerError("Error: Postoperation " + pre.image + " in line " + pre.beginLine
						+ " needs to be a value of type Integer."));
				return VariableType.errorT;
			}
		}
		return atomType;
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		Value atomValue = atom.run(tabelle);
		if (pre != null) {
			int addedValue = pre.image.equals("++") ? 1 : - 1;
			if (atomValue.type.hasSameTypeAs(VariableType.intT)) {
				return new Value(atomValue.i + addedValue);
			} else if (atomValue.type.hasSameTypeAs(VariableType.identifier)) {
				Value readValue = tabelle.find(atomValue.identifier.image).value;
				readValue.i += addedValue;
				return new Value(readValue.i);
			} else {
				throw new RuntimeException("RuntimeException: unknown value for pre increment."); // should not be reached
			}
		} else if (post != null) {
			if (atomValue.type.hasSameTypeAs(VariableType.intT)) {
				return new Value(atomValue.i);
			} else if (atomValue.type.hasSameTypeAs(VariableType.identifier)) {
				Value readValue = tabelle.find(atomValue.identifier.image).value;
				Value returnValue = new Value(readValue.i);
				readValue.i += post.image.equals("++") ? 1 : - 1;
				return returnValue;
			} else {
				throw new RuntimeException("RuntimeException: unknown value for pre increment."); // should not be reached
			}
		} else {
			return atomValue;
		}
	}
}
