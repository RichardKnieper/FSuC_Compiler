package ast;

import ast.node.type.TypeNode;
import jj.Token;

/**
 * Wraps the parameters of a method definition to store the name and type of a parameter at a given index.
 */
public class ParamWrapper{
	int index;
	TypeNode type;
	Token identifier;
	
	public int getIndex() {
		return index;
	}
	
	public TypeNode getType() {
		return type;
	}
	
	public Token getIdentifier() {
		return identifier;
	}

	public ParamWrapper(int index, TypeNode type, Token identifier) {
		super();
		this.index = index;
		this.type = type;
		this.identifier = identifier;
	}
	
	@Override
	public String toString() {
		return index + identifier.image;
		
	}
	
}
