package ast.value;

import ast.VariableType;
import domain.*;
import jj.Token;

/**
 * Represents the return value of a statement/expression. Used when interpreting the AST.
 * VariableType defines the type and therefore what attribute contains the value. All other attributes are set to null.
 */
public class Value {
    public VariableType type;
    public boolean b;
    public int i;
    public String string;
    public char c;
    public Range r;
    public State state;
    public AbstractTransition transition;
    public FiniteAutomata fa;
    public RegularExpression ra;
    public Token identifier;

    public Value() {}

    public Value(boolean b) {
        this.b = b;
        this.type = VariableType.booleanT;
    }

    public Value(int i) {
        this.i = i;
        this.type = VariableType.intT;
    }

    public Value(String string) {
        this.string = string;
        this.type = VariableType.stringT;
    }

    public Value(char c) {
        this.c = c;
        this.type = VariableType.charT;
    }

    public Value(Range r) {
        this.r = r;
        this.type = VariableType.rangeT;
    }

    public Value(State state) {
        this.state = state;
        this.type = VariableType.stateT;
    }

    public Value(AbstractTransition transition) {
        this.transition = transition;
        this.type = VariableType.transitionT;
    }

    public Value(FiniteAutomata fa) {
        this.fa = fa;
        this.type = VariableType.faT;
    }

    public Value(RegularExpression ra) {
        this.ra = ra;
        this.type = VariableType.rangeT;
    }

    public Value(Token token) {
        this.identifier = token;
        this.type = VariableType.identifier;
    }
}
