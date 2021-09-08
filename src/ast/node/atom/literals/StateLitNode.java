package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
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
                errors.add(new CompilerError("Error: " + accept.image + "is not defined in line: "
                        + accept.beginLine));
            } else if (!temp.type.variableType.hasSameTypeAs(VariableType.intT)) {
                errors.add(new CompilerError("Error: " + accept.image + "is not an int in line: "
                        + accept.beginLine));
            } else {
            	return VariableType.stateT;
			}
        }

        return VariableType.errorT;
    }
}
