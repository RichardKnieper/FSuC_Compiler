package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import jj.Token;

import java.util.List;

public class TransitionLitNode extends AtomNode {
	public Token rangeIdentifier, endStateIdentifier;
	public RangeLitNode range;
	public StateLitNode endState;

	public TransitionLitNode(Token rangeIdentifier, Token endStateIdentifier, RangeLitNode range,
			StateLitNode endState) {
		super();
		this.rangeIdentifier = rangeIdentifier;
		this.endStateIdentifier = endStateIdentifier;
		this.range = range;
		this.endState = endState;
	}

	public String toString(String indent) {
		return indent + "TransitionLitNode: " + ((rangeIdentifier != null) ? rangeIdentifier.image : "")
				+ ((range != null) ? range.toString(indent + "\t") : "")
				+ ((endStateIdentifier != null) ? endStateIdentifier.image : "")
				+ ((endState != null) ? endState.toString(indent + "\t") : "");
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean hasError = false;

		if (range == null) {
			DeclNode temp = tabelle.find(rangeIdentifier.image);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + rangeIdentifier.image + "is not defined in line: "
						+ rangeIdentifier.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.rangeT)) {
				errors.add(new CompilerError("Error: " + rangeIdentifier + "is not a Range in line: "
						+ rangeIdentifier.beginLine));
				hasError = true;
			}
		} else {
			if (range.semantischeAnalyse(tabelle, errors).isError()) {
				hasError = true;
			}
		}

		if (endState == null) {
			DeclNode temp = tabelle.find(rangeIdentifier.image);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + rangeIdentifier.image + "is not defined in line: "
						+ rangeIdentifier.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.rangeT)) {
				errors.add(new CompilerError("Error: " + rangeIdentifier + "is not a Range in line: "
						+ rangeIdentifier.beginLine));
				hasError = true;
			}
		} else {
			endState.semantischeAnalyse(tabelle, errors);
		}

		if (rangeIdentifier != null) {
			if (tabelle.find(rangeIdentifier.image) == null)
				errors.add(new CompilerError("Error: Class " + rangeIdentifier.image + " does not exist"));
		} else if (range != null) {
			range.semantischeAnalyse(tabelle, errors);
		}

		if (endStateIdentifier != null) {
			if (tabelle.find(endStateIdentifier.image) == null)
				errors.add(new CompilerError("Error: Class " + endStateIdentifier.image + " does not exist"));
		} else if (endState != null) {
			endState.semantischeAnalyse(tabelle, errors);
		}
		realType = VariableType.transitionT;
	}
}
