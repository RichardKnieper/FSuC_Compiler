
public class VariableType {
	static VariableType booleanT = new VariableType();
	static VariableType stringT = new VariableType();
	static VariableType charT = new VariableType();
	static VariableType intT = new VariableType();
	static VariableType rangeT = new VariableType();
	static VariableType transitionT = new VariableType();
	static VariableType stateT = new VariableType();
	static VariableType faT = new VariableType();
	static VariableType raT = new VariableType();
	static VariableType errorT = new VariableType();

	static class ArrayVariableType extends VariableType {
		VariableType variableType;
		public ArrayVariableType(VariableType variableType) {
			super();
			this.variableType = variableType;
		}
	}
	
	static class SetVariableType extends VariableType {
		VariableType variableType;
		public SetVariableType(VariableType variableType) {
			super();
			this.variableType = variableType;
		}
	}
	
	static class MapVariableType extends VariableType {
		VariableType keyVariableType;
		VariableType valueVariableType;
		public MapVariableType(VariableType keyVariableType, VariableType valueVariableType) {
			super();
			this.keyVariableType = keyVariableType;
			this.valueVariableType = valueVariableType;
		}
	}
	
	
	static public boolean sameTypeAs(MapVariableType first, MapVariableType second) {
		return ((first.keyVariableType == second.keyVariableType) || (first.keyVariableType == charT && second.keyVariableType == intT || first.keyVariableType == intT && second.keyVariableType == charT)) 
				&& ((first.valueVariableType == second.valueVariableType) || (first.valueVariableType == charT && second.valueVariableType == intT || first.valueVariableType == intT && second.valueVariableType == charT)) 
				;
	}
	
	static public boolean sameTypeAs(SetVariableType first, SetVariableType second) {
		return first.variableType == second.variableType  
				|| first.variableType == charT && second.variableType == intT 
				|| first.variableType == intT && second.variableType == charT;
	}
	
	static public boolean sameTypeAs(ArrayVariableType first, ArrayVariableType second) {
		return first.variableType == second.variableType  
				|| first.variableType == charT && second.variableType == intT 
				|| first.variableType == intT && second.variableType == charT;
	}

	static public boolean sameTypeAs(VariableType first, VariableType second) {
		return first == second  
				|| first == charT && second == intT 
				|| first == intT && second == charT;
	}
	
	static VariableType of(String s) {
		switch (s) {
			case "boolean": return VariableType.booleanT;
			case "String": return VariableType.stringT;
			case "char": return VariableType.charT;
			case "int": return VariableType.intT;
			case "Range": return VariableType.rangeT;
			case "Transition": return VariableType.transitionT;
			case "State": return VariableType.stateT;
			case "FA": return VariableType.faT;
			case "RA": return VariableType.raT;
			default:
			return errorT; 
	}
	}
	
	static String printType(VariableType vt) {
		if(vt == VariableType.booleanT)
			return "boolean";
		if(vt == VariableType.stringT)
			return "String";
		if(vt == VariableType.charT)
			return "char";
		if(vt == VariableType.intT)
			return "int";
		if(vt == VariableType.rangeT)
			return "Range";
		if(vt == VariableType.transitionT)
			return "Transition";
		if(vt == VariableType.stateT)
			return "State";
		if(vt == VariableType.faT)
			return "FA";
		if(vt == VariableType.raT)
			return "RA";
		if(vt == VariableType.errorT)
			return "error";
		return "Undefine";
	}
	

	
	
}
