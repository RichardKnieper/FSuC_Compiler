
public class Type {
	static Type booleanT = new Type();
	static Type stringT = new Type();
	static Type charT = new Type();
	static Type intT = new Type();
	static Type rangeT = new Type();
	static Type transitionT = new Type();
	static Type stateT = new Type();
	static Type faT = new Type();
	static Type raT = new Type();
	static Type errorT = new Type();

	class ArrayType extends Type {
		Type type;
		public ArrayType(Type type) {
			super();
			this.type = type;
		}
	}
	
	class SetType extends Type {
		Type type;
		public SetType(Type type) {
			super();
			this.type = type;
		}
	}
	
	class MapType extends Type {
		Type keyType;
		Type valueType;
		public MapType(Type keyType, Type valueType) {
			super();
			this.keyType = keyType;
			this.valueType = valueType;
		}
	}
	
	static boolean sameTypeAs(MapType first, MapType second) {
		return first.keyType == second.keyType && first.valueType == second.valueType;
	}
	
	static boolean sameTypeAs(SetType first, SetType second) {
		return first.type == second.type;
	}
	
	static boolean sameTypeAs(ArrayType first, ArrayType second) {
		return first.type == second.type;
	}

	static boolean sameTypeAs(Type first, Type second) {
		return first == second;
	}
	
	static Type of(String s) {
		switch (s) {
			case "boolean": return Type.booleanT;
			case "String": return Type.stringT;
			case "int": return Type.intT;
			case "Range": return Type.rangeT; 
			case "Transition": return Type.transitionT; 
			case "State": return Type.stateT; 
			case "FA": return Type.faT; 
			case "RA": return Type.raT; 
			default:
			return errorT; 
	}
	}
}
