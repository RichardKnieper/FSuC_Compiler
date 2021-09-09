package ast.node.atom.literals;

import ast.SymbolTabelle;
import ast.VariableType;
import ast.exceptions.CompilerError;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import ast.value.Value;
import domain.State;
import jj.ParserConstants;
import jj.Token;

import java.util.List;

public class StateLitNode extends AtomNode {
    public Token label, accept;

    public StateLitNode(Token label, Token accept) {
        super();
        this.label = label;
        this.accept = accept;
    }

    public String toString(String indent) {
        return indent + "StateLitNode: " + label.image + ((accept != null) ? accept.image : "");
    }

    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        if (accept == null) {
            return VariableType.stateT;
        }

        if (accept.kind == ParserConstants.IntegerLiteral) {
            return VariableType.stateT;
        }

        if (accept.kind == ParserConstants.Identifier) {
            DeclNode temp = tabelle.find(accept.image);
            if (temp == null) {
                errors.add(new CompilerError("Error: " + accept.image + " is not defined in line: "
                        + accept.beginLine));
            } else if (!temp.type.variableType.hasSameTypeAs(VariableType.intT)) {
                errors.add(new CompilerError("Error: " + accept.image + " is not an int in line: "
                        + accept.beginLine));
            } else {
            	return VariableType.stateT;
			}
        }

        return VariableType.errorT;
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        Integer acceptInt;
        if (accept == null) {
            acceptInt = null;
        } else if (accept.kind == ParserConstants.Identifier) {
            acceptInt = tabelle.find(accept.image).value.i;
        } else { // int
            acceptInt = Integer.parseInt(accept.image);
        }
        State state;
        if (acceptInt == null) {
            state = new State(label.image);
        } else {
            state = new State(label.image, acceptInt);
        }
        return new Value(state);
    }
}
