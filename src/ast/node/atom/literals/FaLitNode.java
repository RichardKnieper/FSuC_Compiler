package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import ast.value.Value;
import domain.FiniteAutomata;
import domain.State;
import jj.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the creation of a {@link FiniteAutomata}.
 */
public class FaLitNode extends AtomNode {
	public StateLitNode startState;
	public Token startStateIdentifier;
	public List<TransitionWrapper> transitionWrappers = new LinkedList<>();

	public void add(Token i, TransitionLitNode t) {
		transitionWrappers.add(new TransitionWrapper(i, t));
	}

	static class TransitionWrapper {
		// if transitionLitNode == null -> identifier identifies Transition
		// else -> identifier identifier first state of Transition
		Token identifier;
		TransitionLitNode transitionLitNode;

		public TransitionWrapper(Token identifier, TransitionLitNode transitionLitNode) {
			this.identifier = identifier;
			this.transitionLitNode = transitionLitNode;
		}

		public String toString(String indent) {
			return indent + "Transition " + "\n" + transitionLitNode.toString(indent + "\t") + "\n" + identifier.image;
		}
	}

	public FaLitNode(StateLitNode startState, Token startStateIdentifier) {
		this.startState = startState;
		this.startStateIdentifier = startStateIdentifier;
	}

	public String toString(String indent) {
		/*
		 * String res = indent+"FaLitNode"; res+= startState.toString(); res+=
		 * startStateIdentifier.image; for (Transition trans:transitions) res += "\n"+
		 * trans.toString(indent+"\t"); for (FaLitNode rangeLitNode:additionsFa) res +=
		 * "\n"+ rangeLitNode.toString(indent+"\t"); for (Token
		 * token:additionsIdentifier) res += "\n"+ token.image;
		 * 
		 * return res;
		 */
		return indent + "FaLitNode";
	}

	@SuppressWarnings("DuplicatedCode")
	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean hasError = false;

		if (startState == null) {
			DeclNode temp = tabelle.find(startStateIdentifier.image);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + startStateIdentifier.image + " is not defined in line: "
						+ startStateIdentifier.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.stateT)) {
				errors.add(new CompilerError("Error: " + startStateIdentifier.image + " is not a State in line: "
						+ startStateIdentifier.beginLine));
				hasError = true;
			}
		} else {
			VariableType startStateType = startState.semantischeAnalyse(tabelle, errors);
			if (!startStateType.hasSameTypeAs(VariableType.stateT)) {
				hasError = true;
			}
		}

		for (TransitionWrapper transitionWrapper : transitionWrappers){
			if (transitionWrapper.transitionLitNode == null) {
				DeclNode temp = tabelle.find(transitionWrapper.identifier.image);
				if (temp == null) {
					errors.add(new CompilerError("Error: " + transitionWrapper.identifier.image + " is not defined in line: "
							+ transitionWrapper.identifier.beginLine));
					hasError = true;
				} else if (!temp.type.variableType.hasSameTypeAs(VariableType.transitionT)) {
					errors.add(new CompilerError("Error: " + transitionWrapper.identifier.image + " is not a Transition in line: "
							+ transitionWrapper.identifier.beginLine));
					hasError = true;
				}
			} else {
				VariableType transitionType = transitionWrapper.transitionLitNode.semantischeAnalyse(tabelle, errors);
				if (!transitionType.hasSameTypeAs(VariableType.transitionT)) {
					hasError = true;
				}
			}
		}

		return hasError ? VariableType.errorT : VariableType.faT;
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		State start;
		if (startState != null) {
			start = startState.run(tabelle).state;
		} else {
			start = tabelle.find(startStateIdentifier.image).value.state;
		}

		FiniteAutomata fa = new FiniteAutomata(start);
		transitionWrappers.stream()
				.map(transitionWrapper -> {
					if (transitionWrapper.transitionLitNode != null) {
						return transitionWrapper.transitionLitNode.run(tabelle).transition;
					} else {
						return tabelle.find(transitionWrapper.identifier.image).value.transition;
					}
				})
				.forEach(fa::addTransitions);

		return new Value(fa);
	}
}
