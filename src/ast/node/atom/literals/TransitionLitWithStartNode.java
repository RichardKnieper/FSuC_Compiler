package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
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

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (startStateIdentifier != null) {
			if (tabelle.find(startStateIdentifier.image) == null)
				errors.add(new CompilerError("Error: Class " + startStateIdentifier.image + " does not exist"));
		} else {
			startState.semantischeAnalyse(tabelle, errors);
		}
		if (transitionLitNode != null) {
			transitionLitNode.semantischeAnalyse(tabelle, errors);
		}
		realType = VariableType.transitionT;
	}
}
