import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Resturn STMNT

abstract public class Node {
	public abstract String toString(String indent);

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
	}
}

class CuNode extends Node {
	public List<Node> declOrStmntList = new LinkedList<>();

	public String toString(String indent) {
		String res = indent + "CompilationUnit";
		for (Node node : declOrStmntList)
			res += "\n" + ((node != null) ? node.toString(indent + "\t") : "");
		return res;
	}

	public void semantischeAnalyse(List<CompilerError> errors) {
		semantischeAnalyse(new SymbolTabelle(null), errors);
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		for (Node node : declOrStmntList) {
			node.semantischeAnalyse(tabelle, errors);
		}
	}
}

class BlockStmntNode extends StmntNode {
	public List<Node> declOrStmntList = new LinkedList<>();

	public String toString(String indent) {
		String res = indent + "BlockStatement";
		for (Node node : declOrStmntList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		SymbolTabelle newSt = new SymbolTabelle(tabelle);
		for (Node node : declOrStmntList) {
			node.semantischeAnalyse(newSt, errors);
		}
	}
}

class ReturnStmntNode extends StmntNode {
	ExprNode expr;

	public ReturnStmntNode(ExprNode expr) {
		super();
		this.expr = expr;
	}

	public String toString(String indent) {
		String res = indent + "Return Statement\n";
		expr.toString(indent + "\t");
		return res;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		expr.semantischeAnalyse(tabelle, errors);
	}
}

class IfNode extends StmntNode {
	StmntNode ifExpr, ifStmnt, elseStmnt;

	public IfNode(StmntNode ifExpr, StmntNode ifStmnt, StmntNode elseStmnt) {
		this.ifExpr = ifExpr;
		this.ifStmnt = ifStmnt;
		this.elseStmnt = elseStmnt;
	}

	public String toString(String indent) {
		return indent + "IfStatement\n" + ifExpr.toString(indent + "\t") + "\n" + ifStmnt.toString(indent + "\t")
				+ (elseStmnt != null ? "\n" + elseStmnt.toString(indent + "\t") : "");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		ifExpr.semantischeAnalyse(tabelle, errors);
		if (ifExpr.realType != VariableType.booleanT && ifExpr.realType != VariableType.errorT) {
			errors.add(new CompilerError("Error: IF-condition must be boolean"));
		}
		ifStmnt.semantischeAnalyse(tabelle, errors);
		if (elseStmnt != null)
			elseStmnt.semantischeAnalyse(tabelle, errors);
	}
}

class WhileNode extends StmntNode {
	StmntNode whileExpr, whileStmnt;

	public WhileNode(StmntNode whileExpr, StmntNode whileStmnt) {
		this.whileExpr = whileExpr;
		this.whileStmnt = whileStmnt;
	}

	public String toString(String indent) {
		return indent + "WhileStatement\n" + whileExpr.toString(indent + "\t") + "\n"
				+ whileStmnt.toString(indent + "\t");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		whileExpr.semantischeAnalyse(tabelle, errors);
		if (whileExpr.realType != VariableType.booleanT && whileExpr.realType != VariableType.errorT) {
			errors.add(new CompilerError("Error: WHILE-condition must be boolean"));
		}
		whileStmnt.semantischeAnalyse(tabelle, errors);
	}
}

abstract class StmntNode extends Node {
	VariableType realType = VariableType.errorT;
}

class DeclNode extends Node {
	TypeNode type;
	Token identifier;

	public DeclNode(TypeNode type, Token identifier) {
		super();
		this.type = type;
		this.identifier = identifier;
	}

	public String toString(String indent) {
		return indent + "DeclNode\n" + type.toString(indent + "\t") + "\n" + identifier.image + "\n";
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		// type.semantischeAnalyse(tabelle, errors);
		if (!tabelle.add(identifier.image, this))
			errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
					+ tabelle.find(identifier.image).identifier.beginLine));
	}

}

class VarDeclNode extends DeclNode {
	ExprNode expr;

	public VarDeclNode(TypeNode type, Token identifier, ExprNode expr) {
		super(type, identifier);
		this.expr = expr;
	}

	public String toString(String indent) {
		return indent + "VarDeclNode\n" + expr.toString(indent + "\t") + "\n";
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		// type.semantischeAnalyse(tabelle, errors);
		if (VariableType.sameTypeAs(type.variableType, expr.realType)) {
			if (!tabelle.add(identifier.image, this))
				errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
						+ tabelle.find(identifier.image).identifier.beginLine));
		} else {
			errors.add(new CompilerError("Error: Type mismatch in line: " + identifier.beginLine));
		}
	}
}

class MethDeclNode extends DeclNode {
	Map<Token, ParamWrapper> params = new HashMap<>();

	public MethDeclNode(TypeNode type, Token identifier) {
		super(type, identifier);
	}

	public String toString(String indent) {
		String res = indent + "CompilationUnit";
		for (Map.Entry<Token, ParamWrapper> me : params.entrySet()) {
			res = me.getValue().getType().toString(indent + "\t") + me.getKey().image + "\n";
		}
		return res;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		// type.semantischeAnalyse(tabelle, errors);
		if (!tabelle.add(identifier.image, this))
			errors.add(new CompilerError("Error: " + identifier.image + " already exists in line: "
					+ tabelle.find(identifier.image).identifier.beginLine));
	}
}

//Todo typeNode
class TypeNode extends Node {
	VariableType variableType;

	public TypeNode(VariableType variableType) {
		super();
		this.variableType = variableType;
	}

	public String toString(String indent) {
		if (variableType.sameTypeAs(variableType, VariableType.intT)) {

		}
		return indent + "TypeNode" + "\n";
	}

}

//Todo typeNode
class SetTypeNode extends TypeNode {
	public SetTypeNode(VariableType variableType) {
		super(variableType);
	}

	public String toString(String indent) {
		return indent + "SetTypeNode" + "\n";
	}
}

//Todo typeNode
class ArrayTypeNode extends TypeNode {
	public ArrayTypeNode(VariableType variableType) {
		super(variableType);
	}

	public String toString(String indent) {
		return indent + "ArrayTypeNode" + "\n";
	}
}

//Todo typeNode
class MapTypeNode extends TypeNode {
	VariableType key, value;

	public MapTypeNode(VariableType key, VariableType value) {
		super(null);
		this.key = key;
		this.value = value;
	}

	public String toString(String indent) {
		return indent + "MapTypeNode" + "\n";
	}
}

abstract class AtomNode extends Node {
	VariableType realType = VariableType.errorT;
}

class IdentifierNode extends AtomNode {
	Token identifier;

	public IdentifierNode(Token identifier) {
		this.identifier = identifier;
	}

	public String toString(String indent) {
		return indent + "IdentifierNode: " + identifier.image;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (tabelle.find(identifier.image) == null)
			errors.add(new CompilerError("Error: " + identifier.image + " cannot be resolved to a variable"));
	}
}

class NegationNode extends AtomNode {
	AtomNode node;

	public NegationNode(AtomNode node) {
		super();
		this.node = node;
	}

	public String toString(String indent) {
		return indent + "NegationNode\n" + node.toString(indent + "\t");
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		node.semantischeAnalyse(tabelle, errors);
		if (node.realType != VariableType.booleanT) {
			errors.add(new CompilerError(
					"Error: NegationNode, the operator ! is only defined for argument of type boolean"));
		}
	}
}

class ReadClassAttributeNode extends AtomNode {
	Token classIdentifier, attributeIdentifier;

	public ReadClassAttributeNode(Token classIdentifier, Token attributeIdentifier) {
		this.classIdentifier = classIdentifier;
		this.attributeIdentifier = attributeIdentifier;
	}

	public String toString(String indent) {
		return indent + "ReadClassAttributeNode: " + classIdentifier + "." + attributeIdentifier + "\n";
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		if (tabelle.find(classIdentifier.image) == null) {
			errors.add(new CompilerError("Error: Class " + classIdentifier.image + " does not exist"));
		}
	}
}

class LitNode extends AtomNode {
	Token token;

	public LitNode(Token token) {
		super();
		this.token = token;
	}

	public String toString(String indent) {
		return indent + "LitNode: " + token.image;
	}

	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		switch (token.kind) {
		case 37:
			realType = VariableType.booleanT;
		case 38:
			realType = VariableType.charT;
		case 39:
			realType = VariableType.stringT;
		case 40:
			realType = VariableType.intT;
		default:
			realType = VariableType.errorT;
		}
	}
}

class SetLitNode extends AtomNode {
	List<AtomNode> elementList = new LinkedList<>();

	public String toString(String indent) {
		String res = indent + "SetLitNode";
		for (AtomNode node : elementList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}
	
	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		for (AtomNode node : elementList) {
			node.semantischeAnalyse(tabelle, errors);
		}
	}
}

class ArrayLitNode extends AtomNode {
	List<AtomNode> elementList = new LinkedList<>();

	public String toString(String indent) {
		String res = indent + "ArrayLitNode";
		for (AtomNode node : elementList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}
	public void semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean temp = true;
		for (int i=0;i<elementList.size()-1;i++) {
			temp = temp && VariableType.sameTypeAs(elementList.get(i).realType, (elementList.get(i+1).realType));			
		}
		if(!temp) {
			errors.add(new CompilerError("Error: The elements in an array must be of the same type."));			
		}
	}
}

class MapLitNode extends AtomNode {
	Map<AtomNode, AtomNode> elements = new HashMap<>();

	public String toString(String indent) {
		String res = indent + "MapLitNode";
		for (Map.Entry<AtomNode, AtomNode> me : elements.entrySet()) {
			res = me.getKey().toString(indent + "\t") + me.getValue().toString(indent + "\t") + "\n";
		}
		return res;
	}

}

class MethCallNode extends AtomNode {
	Token identifier;
	List<AtomNode> elementList = new LinkedList<>();

	public MethCallNode() {
	}

	public String toString(String indent) {
		String res = indent + "MethCallNode" + identifier.image + "\n";
		for (AtomNode node : elementList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}
}

class ClassMethCallNode extends MethCallNode {
	Token objectIdentifier;
	MethCallNode methCallNode;

	public ClassMethCallNode(Token objectIdentifier, MethCallNode methCallNode) {
		this.objectIdentifier = objectIdentifier;
		this.methCallNode = methCallNode;
	}

	public String toString(String indent) {
		return indent + "ClassMethCallNode:\n" + ((objectIdentifier != null) ? objectIdentifier.image : "")
				+ methCallNode.toString(indent + "\t") + "\n";
	}
}

class StateLitNode extends AtomNode {
	Token label, accept;

	public StateLitNode(Token label, Token accept) {
		super();
		this.label = label;
		this.accept = accept;
	}

	public String toString(String indent) {
		return indent + "StateLitNode: " + label.image + ((accept != null) ? accept.image : "");
	}
}

class TransitionLitNode extends AtomNode {
	Token rangeIdentifier, endStateIdentifier;
	RangeLitNode range;
	StateLitNode endState;

	public TransitionLitNode(Token rangeIdentifier, Token endStateIdentifier, RangeLitNode range,
			StateLitNode endState) {
		super();
		this.rangeIdentifier = rangeIdentifier;
		this.endStateIdentifier = endStateIdentifier;
		this.range = range;
		this.endState = endState;
	}

	public String toString(String indent) {
		return indent + "TransitionLitNode: " + ((rangeIdentifier != null) ? rangeIdentifier.image : "")
				+ ((range != null) ? range.toString(indent + "\t") : "")
				+ ((endStateIdentifier != null) ? endStateIdentifier.image : "")
				+ ((endState != null) ? endState.toString(indent + "\t") : "");
	}
}

class TransitionLitWithStartNode extends AtomNode {
	Token startStateIdentifier;
	StateLitNode startState;
	TransitionLitNode transitionLitNode;

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

		public String toString(String indent) {
			return indent + "Range " + "\n" + a.image + "\n" + b.image;
		}
	}

	public String toString(String indent) {
		/*
		 * String res = indent + "RangeLitNode"; for (Range range:ranges) res += "\n"+
		 * range.toString(indent+"\t"); for (RangeLitNode rangeLitNode:additionsRange)
		 * res += "\n"+ rangeLitNode.toString(indent+"\t"); for (Token
		 * token:additionsIdentifier) res += "\n"+ token.image; return res;
		 */
		return indent + "RangeLitNode";
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
}

abstract class ExprNode extends StmntNode {

}

class BracketExpr extends ExprNode {
	ExprNode expr;

	public BracketExpr(ExprNode expr) {
		this.expr = expr;
	}

	public String toString(String indent) {
		return indent + "BracketExpr " + "\n" + ((expr != null) ? expr.toString(indent + "\t") : "");
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

	public String toString(String indent) {
		return indent + "AndOrExpr " + "\n" + ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
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

	public String toString(String indent) {
		return indent + "CompExprNode " + ((op != null) ? op.image : "") + "\n"
				+ ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
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

	public String toString(String indent) {
		return indent + "SumExprNode " + ((op != null) ? op.image : "") + "\n"
				+ ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
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

	public String toString(String indent) {
		return indent + "ProdExprNode " + ((op != null) ? op.image : "") + "\n"
				+ ((expr != null) ? expr.toString(indent + "\t") : "") + "\n"
				+ ((secondExpr != null) ? secondExpr.toString(indent + "\t") : "");
	}
}

class IntersectionExprNode extends ExprNode {
	PreOrPostIncrementExprNode a;
	PreOrPostIncrementExprNode b;

	public IntersectionExprNode(PreOrPostIncrementExprNode a, PreOrPostIncrementExprNode b) {
		this.a = a;
		this.b = b;
	}

	public String toString(String indent) {
		return indent + "IntersectionExprNode " + "\n" + ((a != null) ? a.toString(indent + "\t") : "") + "\n"
				+ ((b != null) ? b.toString(indent + "\t") : "");
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

	public String toString(String indent) {
		return indent + "IntersectionExprNode " + "\n" + atom.toString(indent + "\t") + "\n"
				+ ((pre != null) ? pre.image : "") + ((post != null) ? post.image : "");
	}
}