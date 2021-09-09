package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import jj.Token;

import java.util.LinkedList;
import java.util.List;

public class FaLitNode extends AtomNode {
	public StateLitNode startState;
	public Token startStateIdentifier;
	public List<Transition> transitions = new LinkedList<>();

	// for FA + FA
	public List<Token> additionsIdentifier = new LinkedList<>();
	public List<FaLitNode> additionsFa = new LinkedList<>();

	public void add(Token i) {
		additionsIdentifier.add(i);
	}

	public void add(FaLitNode fa) {
		additionsFa.add(fa);
	}

	public void add(Token i, TransitionLitNode t) {
		transitions.add(new Transition(i, t));
	}

	static class Transition {
		// if transitionLitNode == null -> identifier identifies Transition
		// else -> identifier identifier first state of Transition
		Token identifier;
		TransitionLitNode transitionLitNode;

		public Transition(Token identifier, TransitionLitNode transitionLitNode) {
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

		for (Transition transition : transitions){
			if (transition.transitionLitNode == null) {
				DeclNode temp = tabelle.find(transition.identifier.image);
				if (temp == null) {
					errors.add(new CompilerError("Error: " + transition.identifier.image + " is not defined in line: "
							+ transition.identifier.beginLine));
					hasError = true;
				} else if (!temp.type.variableType.hasSameTypeAs(VariableType.transitionT)) {
					errors.add(new CompilerError("Error: " + transition.identifier.image + " is not a Transition in line: "
							+ transition.identifier.beginLine));
					hasError = true;
				}
			} else {
				VariableType transitionType = transition.transitionLitNode.semantischeAnalyse(tabelle, errors);
				if (!transitionType.hasSameTypeAs(VariableType.transitionT)) {
					hasError = true;
				}
			}
		}

		boolean errorInAdditionalFa = additionsFa.stream()
				.anyMatch(addition -> addition.semantischeAnalyse(tabelle, errors).isError());
		if (errorInAdditionalFa) {
			hasError = true;
		}

		for (Token i : additionsIdentifier) {
			String identifier = i.image;
			DeclNode temp = tabelle.find(identifier);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + identifier + " is not defined in line: "
						+ i.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.faT)) {
				errors.add(new CompilerError("Error: " + identifier + " is not a FA in line: "
						+ i.beginLine));
				hasError = true;
			}
		}

		return hasError ? VariableType.errorT : VariableType.faT;
	}
}
