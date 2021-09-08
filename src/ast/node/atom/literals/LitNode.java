package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
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
}
