import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
	
}

class CuNode extends Node{
	public List<Node> declStmntList = new LinkedList<>();
}

class BlockStmntNode extends StmntNode{
	public List<Node> declStmntList = new LinkedList<>();
}

class ReturnStmntNode extends StmntNode{
	ExprNode expr;

	public ReturnStmntNode(ExprNode expr) {
		super();
		this.expr = expr;
	}
	
}

class IfNode extends StmntNode{
	StmntNode ifExpr, ifStmnt, elseStmnt;
	public IfNode(StmntNode ifExpr,StmntNode ifStmnt,StmntNode elseStmnt) {
		this.ifExpr = ifExpr;
		this.ifStmnt = ifStmnt;
		this.elseStmnt = elseStmnt;
	}
}

class WhileNode extends StmntNode{
	StmntNode whileExpr, whileStmnt;
	public WhileNode(StmntNode whileExpr,StmntNode whileStmnt) {
		this.whileExpr = whileExpr;
		this.whileStmnt = whileStmnt;		
	}
}




class StmntNode extends Node{
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

class VarDeclNode extends DeclNode{
	ExprNode expr;
	public VarDeclNode(TypeNode type, Token identifier, ExprNode expr) {
		super(type, identifier);
		this.expr = expr;
		// TODO Auto-generated constructor stub
	}
	
}

class MethDeclNode extends DeclNode{
	Map<Token, TypeNode> params = new HashMap<>();
	public MethDeclNode(TypeNode type, Token identifier) {
		super(type, identifier);
		// TODO Auto-generated constructor stub
	}
	
}

class TypeNode extends Node{
	Type type;
	public TypeNode(Type type) {
		super();
		this.type = type;
	}
}

class SetTypeNode extends TypeNode{
	public SetTypeNode(Type type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
}

class ArrayTypeNode extends TypeNode{
	public ArrayTypeNode(Type type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
}



class MapTypeNode extends TypeNode {
	Type key, value;
	public MapTypeNode(Type key,Type value) {
		super(null);
		this.key = key;
		this.value = value;
	}

}

class AtomNode extends Node {
	
}

class NegationNode extends AtomNode {
	AtomNode node;
	public NegationNode(AtomNode node) {
		super();
		this.node = node;
	}
}

class LitNode extends AtomNode
{
	Token token;
	public LitNode(Token token) {
		super();
		this.token = token;
	}
}

class SetLitNode extends AtomNode{
	List<LitNode> elementList = new LinkedList<LitNode>();	
}

class ArrayLitNode extends AtomNode{
	List<LitNode> elementList = new LinkedList<LitNode>();	
}

class MapLitNode extends AtomNode {
	Map<AtomNode, AtomNode> elements = new HashMap<>();
}

class MethCallNode extends AtomNode{
	Token t;
	List<LitNode> elementList = new LinkedList<LitNode>();	
	public MethCallNode() {}
	
}

class StateLitNode extends AtomNode {
	Token label, accept;

	public StateLitNode(Token label, Token accept) {
		super();
		this.label = label;
		this.accept = accept;
	}	
}

class TransitionLitNode extends AtomNode{
	Token rangeIdentifier, stateIdentifier;
	RangeLitNode range;
	StateLitNode state;
	public TransitionLitNode(Token rangeIdentifier, Token stateIdentifier, RangeLitNode range, StateLitNode state) {
		super();
		this.rangeIdentifier = rangeIdentifier;
		this.stateIdentifier = stateIdentifier;
		this.range = range;
		this.state = state;
	}
}

class RangeLitNode extends AtomNode{
	
}



// Node - DeclNote - varDel - methDecl