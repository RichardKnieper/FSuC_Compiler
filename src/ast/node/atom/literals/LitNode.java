package ast.node.atom.literals;

import ast.SymbolTabelle;
import ast.VariableType;
import ast.exceptions.CompilerError;
import ast.node.atom.AtomNode;
import ast.value.Value;
import jj.ParserConstants;
import jj.Token;

import java.util.List;

public class LitNode extends AtomNode {
    public Token token;

    public LitNode(Token token) {
        super();
        this.token = token;
    }

    public String toString(String indent) {
        return indent + "LitNode: " + token.image;
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        switch (token.kind) {
            case ParserConstants.BoolLiteral: return VariableType.booleanT;
            case ParserConstants.CharLiteral: return VariableType.charT;
            case ParserConstants.StringLiteral: return VariableType.stringT;
            case ParserConstants.IntegerLiteral: return VariableType.intT;
            default: return VariableType.errorT;
        }
    }

    @Override
    public Value run(SymbolTabelle tabelle) {
        switch (token.kind) {
            case ParserConstants.BoolLiteral: return new Value(Boolean.parseBoolean(token.image));
            case ParserConstants.CharLiteral: return new Value(token.image.charAt(1));
            case ParserConstants.StringLiteral: return new Value(token.image.substring(1, token.image.length() - 2));
            case ParserConstants.IntegerLiteral: return new Value(Integer.parseInt(token.image));
            default: Value returnValue = new Value(); returnValue.type = VariableType.errorT; return returnValue;
        }
    }
}
