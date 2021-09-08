package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import jj.Token;

import java.util.List;

public class TransitionLitWithStartNode extends AtomNode {
	public Token startStateIdentifier;
	public StateLitNode startState;
	public TransitionLitNode transitionLitNode;

	public TransitionLitWithStartNode(Token startStateIdentifier, StateLitNode startState,
			TransitionLitNode transitionLitNode) {
		this.startStateIdentifier = startStateIdentifier;
		this.startState = startState;
		this.transitionLitNode = transitionLitNode;
	}

	public String toString(String indent) {
		return indent + "TransitionLitWithStartNode:"
				+ ((startStateIdentifier != null) ? startStateIdentifier.image : "")
				+ ((startState != null) ? startState.toString(indent + "\t") : "")
				+ ((transitionLitNode != null) ? transitionLitNode.toString(indent + "\t") : "");
	}

	@SuppressWarnings("DuplicatedCode")
	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean hasError = false;

		if (startStateIdentifier != null) {
			DeclNode temp = tabelle.find(startStateIdentifier.image);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + startStateIdentifier.image + "is not defined in line: "
						+ startStateIdentifier.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.stateT)) {
				errors.add(new CompilerError("Error: " + startStateIdentifier.image + "is not a State in line: "
						+ startStateIdentifier.beginLine));
				hasError = true;
			}
		} else {
			if (startState.semantischeAnalyse(tabelle, errors).isError()) {
				hasError = true;
			}
		}

		if (transitionLitNode.semantischeAnalyse(tabelle, errors).isError()) {
			hasError = true;
		}

		return hasError ? VariableType.errorT : VariableType.transitionT;
	}
}
