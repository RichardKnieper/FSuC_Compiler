package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
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

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		realType = VariableType.faT;
	}
}
