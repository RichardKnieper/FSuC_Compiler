package ast;

/**
 * Represents the Type a variable can have.
 */
@SuppressWarnings("InstantiationOfUtilityClass")
public class VariableType {
	// possible types a variable can have or return types a method can have
	public static VariableType booleanT = new VariableType();
	public static VariableType stringT = new VariableType();
	public static VariableType charT = new VariableType();
	public static VariableType intT = new VariableType();
	public static VariableType rangeT = new VariableType();
	public static VariableType transitionT = new VariableType();
	public static VariableType stateT = new VariableType();
	public static VariableType faT = new VariableType();
	public static VariableType raT = new VariableType();

	public static VariableType errorT = new VariableType(); // needed for semantic analysis
	public static VariableType noReturnType = new VariableType(); // needed for semantic analyses
	public static VariableType identifier = new VariableType(); // needed for interpreter

	/**
	 * Represents the type of an array that contains values of a given {@link VariableType}.
	 */
	public static class ArrayVariableType extends VariableType {
		public VariableType variableType;
		public ArrayVariableType(VariableType variableType) {
			super();
			this.variableType = variableType;
		}
	}

	/**
	 * Represents the type of a set that contains values of a given {@linke VariableType}.
	 */
	public static class SetVariableType extends VariableType {
		public VariableType variableType;
		public SetVariableType(VariableType variableType) {
			super();
			this.variableType = variableType;
		}
	}

	/**
	 * Represents the type of a map that maps keys of a given {@linke VariableType} to values of a given {@linke VariableType}.
	 */
	public static class MapVariableType extends VariableType {
		public VariableType keyVariableType;
		public VariableType valueVariableType;
		public MapVariableType(VariableType keyVariableType, VariableType valueVariableType) {
			super();
			this.keyVariableType = keyVariableType;
			this.valueVariableType = valueVariableType;
		}
	}

	static public boolean hasSameTypeAs(VariableType first, VariableType second) {
		if (first.isMapType()) {
			if (second.isMapType()) {
				return ((MapVariableType) first).keyVariableType.hasSameTypeAs(((MapVariableType) second).keyVariableType)
						&& ((MapVariableType) first).valueVariableType.hasSameTypeAs(((MapVariableType) second).valueVariableType);
			} else {
				return false;
			}
		}
		if (first.isSetType()) {
			if (second.isSetType()) {
				return ((SetVariableType) first).variableType.hasSameTypeAs(((SetVariableType) second).variableType);
			} else {
				return false;
			}
		}
		if (first.isArrayType()) {
			if (second.isArrayType()) {
				return ((ArrayVariableType) first).variableType.hasSameTypeAs(((ArrayVariableType) second).variableType);
			} else {
				return false;
			}
		}
		return first == second;
	}

	public boolean hasSameTypeAs(VariableType other) {
		return VariableType.hasSameTypeAs(this, other);
	}

	public boolean isMapType() {
		return this instanceof MapVariableType;
	}

	public boolean isSetType() {
		return this instanceof SetVariableType;
	}

	public boolean isArrayType() {
		return this instanceof ArrayVariableType;
	}

	public boolean isError() {
		return this == VariableType.errorT;
	}
	
	@SuppressWarnings("EnhancedSwitchMigration")
	public static VariableType of(String s) {
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
	
	public static String printType(VariableType vt) {
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

	@Override
	public String toString() {
		return VariableType.printType(this);
	}
}
