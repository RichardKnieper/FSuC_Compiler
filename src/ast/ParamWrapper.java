package ast;

import ast.node.type.TypeNode;
import jj.Token;

public class ParamWrapper{
	int index;
	TypeNode type;
	Token indentifier;
	
	public int getIndex() {
		return index;
	}
	
	public TypeNode getType() {
		return type;
	}
	
	public Token getIndentifier() {
		return indentifier;
	}
	public ParamWrapper(int index, TypeNode type, Token indentifier) {
		super();
		this.index = index;
		this.type = type;
		this.indentifier = indentifier;
	}
	
	@Override
	public String toString() {
		return index + indentifier.image;
		
	}
	
}
