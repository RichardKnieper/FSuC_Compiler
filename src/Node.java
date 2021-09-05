import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

}

class CuNode extends Node {
    public List<Node> declOrStmntList = new LinkedList<>();
}

class BlockStmntNode extends StmntNode {
    public List<Node> declOrStmntList = new LinkedList<>();
}

class ReturnStmntNode extends StmntNode {
    ExprNode expr;

    public ReturnStmntNode(ExprNode expr) {
        super();
        this.expr = expr;
    }

}

class IfNode extends StmntNode {
    StmntNode ifExpr, ifStmnt, elseStmnt;

    public IfNode(StmntNode ifExpr, StmntNode ifStmnt, StmntNode elseStmnt) {
        this.ifExpr = ifExpr;
        this.ifStmnt = ifStmnt;
        this.elseStmnt = elseStmnt;
    }
}

class WhileNode extends StmntNode {
    StmntNode whileExpr, whileStmnt;

    public WhileNode(StmntNode whileExpr, StmntNode whileStmnt) {
        this.whileExpr = whileExpr;
        this.whileStmnt = whileStmnt;
    }
}


class StmntNode extends Node {
}

class DeclNode extends Node {
    TypeNode type;
    Token identifier;

    public DeclNode(TypeNode type, Token identifier) {
        super();
        this.type = type;
        this.identifier = identifier;
    }
}

class VarDeclNode extends DeclNode {
    ExprNode expr;
    public VarDeclNode(TypeNode type, Token identifier, ExprNode expr) {
        super(type, identifier);
        this.expr = expr;
    }

}

class MethDeclNode extends DeclNode {
    Map<Token, TypeNode> params = new HashMap<>();
    public MethDeclNode(TypeNode type, Token identifier) {
        super(type, identifier);
    }
}

class TypeNode extends Node {
    VariableType variableType;
    public TypeNode(VariableType variableType) {
        super();
        this.variableType = variableType;
    }
}

class SetTypeNode extends TypeNode {
    public SetTypeNode(VariableType variableType) {
        super(variableType);
    }
}

class ArrayTypeNode extends TypeNode {
    public ArrayTypeNode(VariableType variableType) {
        super(variableType);
    }
}


class MapTypeNode extends TypeNode {
    VariableType key, value;
    public MapTypeNode(VariableType key, VariableType value) {
        super(null);
        this.key = key;
        this.value = value;
    }

}

class AtomNode extends Node {

}

class IdentifierNode extends AtomNode {
    Token identifier;
    public IdentifierNode(Token identifier) {
        this.identifier = identifier;
    }
}

class NegationNode extends AtomNode {
    AtomNode node;
    public NegationNode(AtomNode node) {
        super();
        this.node = node;
    }
}

class ReadClassAttributeNode extends AtomNode {
    Token classIdentifier, attributeIdentifier;
    public ReadClassAttributeNode(Token classIdentifier, Token attributeIdentifier) {
        this.classIdentifier = classIdentifier;
        this.attributeIdentifier = attributeIdentifier;
    }
}

class LitNode extends AtomNode {
    Token token;
    public LitNode(Token token) {
        super();
        this.token = token;
    }
}

class SetLitNode extends AtomNode {
    List<AtomNode> elementList = new LinkedList<>();
}

class ArrayLitNode extends AtomNode {
    List<AtomNode> elementList = new LinkedList<>();
}

class MapLitNode extends AtomNode {
    Map<AtomNode, AtomNode> elements = new HashMap<>();
}

class MethCallNode extends AtomNode {
    Token identifier;
    List<AtomNode> elementList = new LinkedList<>();
    public MethCallNode() {
    }
}

class ClassMethCallNode extends MethCallNode {
    Token objectIdentifier;
    MethCallNode methCallNode;
    public ClassMethCallNode(Token objectIdentifier, MethCallNode methCallNode) {
        this.objectIdentifier = objectIdentifier;
        this.methCallNode = methCallNode;
    }
}

class StateLitNode extends AtomNode {
    Token label, accept;
    public StateLitNode(Token label, Token accept) {
        super();
        this.label = label;
        this.accept = accept;
    }
}

class TransitionLitNode extends AtomNode {
    Token rangeIdentifier, endStateIdentifier;
    RangeLitNode range;
    StateLitNode endState;

    public TransitionLitNode(Token rangeIdentifier, Token endStateIdentifier, RangeLitNode range, StateLitNode endState) {
        super();
        this.rangeIdentifier = rangeIdentifier;
        this.endStateIdentifier = endStateIdentifier;
        this.range = range;
        this.endState = endState;
    }
}

class TransitionLitWithStartNode extends AtomNode {
    Token startStateIdentifier;
    StateLitNode startState;
    TransitionLitNode transitionLitNode;
    public TransitionLitWithStartNode(Token startStateIdentifier, StateLitNode startState, TransitionLitNode transitionLitNode) {
        this.startStateIdentifier = startStateIdentifier;
        this.startState = startState;
        this.transitionLitNode = transitionLitNode;
    }
}

class RangeLitNode extends AtomNode {
    List<Range> ranges = new LinkedList<>();

    // for Range + Range
    List<RangeLitNode> additionsRange = new LinkedList<>();
    List<Token> additionsIdentifier = new LinkedList<>();

    void add(Token a, Token b) {
        ranges.add(new Range(a, b));
    }

    void add(Token i) {
        additionsIdentifier.add(i);
    }

    void add(RangeLitNode r) {
        additionsRange.add(r);
    }

    static class Range {
        Token a, b;
        public Range(Token a, Token b) {
            this.a = a;
            this.b = b;
        }
    }
}
class FaLitNode extends AtomNode {
    StateLitNode startState;
    Token startStateIdentifier;
    List<Transition> transitions = new LinkedList<>();

    // for FA + FA
    List<Token> additionsIdentifier = new LinkedList<>();
    List<FaLitNode> additionsFa = new LinkedList<>();

    void add(Token i) {
        additionsIdentifier.add(i);
    }

    void add(FaLitNode fa) {
        additionsFa.add(fa);
    }

    void add(Token i, TransitionLitNode t) {
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
    }

    public FaLitNode(StateLitNode startState, Token startStateIdentifier) {
        this.startState = startState;
        this.startStateIdentifier = startStateIdentifier;
    }
}

class ExprNode extends StmntNode {

}

class BracketExpr extends ExprNode {
    ExprNode expr;
    public BracketExpr(ExprNode expr) {
        this.expr = expr;
    }
}

class AndOrExpr extends ExprNode {
    CompExprNode expr;
    Token op;
    CompExprNode secondExpr;
    public AndOrExpr(CompExprNode expr, Token op, CompExprNode secondExpr) {
        this.expr = expr;
        this.op = op;
        this.secondExpr = secondExpr;
    }
}

class CompExprNode extends ExprNode {
    SumExprNode expr;
    Token op;
    SumExprNode secondExpr;
    public CompExprNode(SumExprNode expr, Token op, SumExprNode secondExpr) {
        this.expr = expr;
        this.op = op;
        this.secondExpr = secondExpr;
    }
}

class SumExprNode extends ExprNode {
    ProdExprNode expr;
    Token op;
    ProdExprNode secondExpr;
    public SumExprNode(ProdExprNode expr, Token op, ProdExprNode secondExpr) {
        this.expr = expr;
        this.op = op;
        this.secondExpr = secondExpr;
    }

}

class ProdExprNode extends ExprNode {
    IntersectionExprNode expr;
    Token op;
    IntersectionExprNode secondExpr;
    public ProdExprNode(IntersectionExprNode expr, Token op, IntersectionExprNode secondExpr) {
        this.expr = expr;
        this.op = op;
        this.secondExpr = secondExpr;
    }
}

class IntersectionExprNode extends ExprNode {
    PreOrPostIncrementExprNode a;
    PreOrPostIncrementExprNode b;
    public IntersectionExprNode(PreOrPostIncrementExprNode a, PreOrPostIncrementExprNode b) {
        this.a = a;
        this.b = b;
    }
}

class PreOrPostIncrementExprNode extends ExprNode {
    AtomNode atom;
    Token pre;
    Token post;
    public PreOrPostIncrementExprNode(AtomNode atom, Token pre, Token post) {
        this.atom = atom;
        this.pre = pre;
        this.post = post;
    }
}
