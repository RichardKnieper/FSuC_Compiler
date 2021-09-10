package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import ast.value.Value;
import jj.Token;

import java.util.List;

// Only exists when encapsulated by TransitionLitWithStartNode
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

	@SuppressWarnings("DuplicatedCode")
	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean hasError = false;

		if (rangeIdentifier != null) {
			DeclNode temp = tabelle.find(rangeIdentifier.image);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + rangeIdentifier.image + " is not defined in line: "
						+ rangeIdentifier.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.rangeT)) {
				errors.add(new CompilerError("Error: " + rangeIdentifier.image + " is not a Range in line: "
						+ rangeIdentifier.beginLine));
				hasError = true;
			}
		} else if (range != null){
			if (range.semantischeAnalyse(tabelle, errors).isError()) {
				hasError = true;
			}
		}

		if (endState == null) {
			DeclNode temp = tabelle.find(endStateIdentifier.image);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + rangeIdentifier.image + " is not defined in line: "
						+ endStateIdentifier.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.stateT)) {
				errors.add(new CompilerError("Error: " + endStateIdentifier.image + " is not a State in line: "
						+ endStateIdentifier.beginLine));
				hasError = true;
			}
		} else {
			if (endState.semantischeAnalyse(tabelle, errors).isError()) {
				hasError = true;
			}
		}

		return hasError ? VariableType.errorT : VariableType.transitionT;
	}

	// should never be called cause it is only used in TransitionLitWithStartNode
	@Override
	public Value run(SymbolTabelle tabelle) {
		throw new RuntimeException("Should never be called");
	}
}
