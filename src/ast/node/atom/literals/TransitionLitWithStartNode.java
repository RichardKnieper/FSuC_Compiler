package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import ast.value.Value;
import domain.EpsilonTransition;
import domain.Range;
import domain.State;
import domain.Transition;
import jj.Token;

import java.util.List;

/**
 * Uses TransitionLitNode and a start State to represent a Transition.
 */
public class TransitionLitWithStartNode extends AtomNode {
	// either identifier or not is set; the other is null
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

	@Override
	public Value run(SymbolTabelle tabelle) {
		State start;
		if (startState != null) {
			start = startState.run(tabelle).state;
		} else {
			start = tabelle.find(startStateIdentifier.image).value.state;
		}

		State end;
		if (transitionLitNode.endState != null) {
			end = transitionLitNode.endState.run(tabelle).state;
		} else {
			end = tabelle.find(transitionLitNode.endStateIdentifier.image).value.state;
		}

		Range range;
		if (transitionLitNode.range != null) {
			range = transitionLitNode.range.run(tabelle).r;
		} else if (transitionLitNode.rangeIdentifier != null){
			range = tabelle.find(transitionLitNode.rangeIdentifier.image).value.r;
		} else {
			return new Value(new EpsilonTransition(start, end));
		}

		return new Value(new Transition(start, end, range));
	}
}
