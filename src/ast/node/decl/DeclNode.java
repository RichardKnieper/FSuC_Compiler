package ast.node.decl;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.Node;
import ast.node.type.TypeNode;
import ast.value.Value;
import domain.FiniteAutomata;
import domain.Range;
import domain.State;
import domain.Transition;
import jj.Token;

import java.util.List;

public class DeclNode extends Node {
	public TypeNode type;
	public Token identifier;
	public Value value = null;

	public DeclNode(TypeNode type, Token identifier) {
		super();
		this.type = type;
		this.identifier = identifier;
	}

	public String toString(String indent) {
		return indent + "DeclNode\n" + type.toString(indent + "\t") + "\n" + identifier.image + "\n";
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		type.semantischeAnalyse(tabelle, errors);

		String varName = identifier.image;
		if (tabelle.findInCurrentBlock(varName) != null) {
			errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
					+ tabelle.find(identifier.image).identifier.beginLine + " and " + identifier.beginLine));
			return VariableType.errorT;
		} else {
			tabelle.add(varName, this);
			return VariableType.noReturnType;
		}
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		VariableType t = this.type.variableType;
		if (t.hasSameTypeAs(VariableType.intT)) {
			this.value = new Value(0);
		} else if (t.hasSameTypeAs(VariableType.stringT)) {
			this.value = new Value("");
		} else if (t.hasSameTypeAs(VariableType.charT)) {
			this.value = new Value(' ');
		} else if (t.hasSameTypeAs(VariableType.booleanT)) {
			this.value = new Value(false);
		} else if (t.hasSameTypeAs(VariableType.rangeT)) {
			this.value = new Value(new Range());
		} else if (t.hasSameTypeAs(VariableType.stateT)) {
			this.value = new Value(new State(""));
		} else if (t.hasSameTypeAs(VariableType.transitionT)) {
			this.value = new Value(new Transition(new State(""), new State(""), new Range()));
		} else if (t.hasSameTypeAs(VariableType.faT)) {
			this.value = new Value(new FiniteAutomata(new State("")));
		} else if (t.hasSameTypeAs(VariableType.raT)) {
			// TODO
		}
		tabelle.add(identifier.image, this);

		Value returnValue = new Value();
		returnValue.type = VariableType.noReturnType;
		return returnValue;
	}
}
