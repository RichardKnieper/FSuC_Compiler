package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import ast.value.Value;
import domain.BasicRange;
import domain.Range;
import jj.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the creation of a {@link Range}.
 * Also represents the addition of other ranges in the same expression.
 */
public class RangeLitNode extends AtomNode {
	public List<RangeWrapper> rangeWrappers = new LinkedList<>();

	// for Range + Range
	public List<RangeLitNode> additionsRange = new LinkedList<>();
	public List<Token> additionsIdentifier = new LinkedList<>();

	public void add(Token a, Token b) {
		rangeWrappers.add(new RangeWrapper(a, b));
	}

	public void add(Token i) {
		additionsIdentifier.add(i);
	}

	public void add(RangeLitNode r) {
		additionsRange.add(r);
	}

	static class RangeWrapper {
		Token a, b;

		public RangeWrapper(Token a, Token b) {
			this.a = a;
			this.b = b;
		}

		public String toString(String indent) {
			return indent + "Range " + "\n" + a.image + "\n" + b.image;
		}

		public boolean valid() {
			if (b == null) {
				return true;
			}
			return a.image.charAt(1) <= b.image.charAt(1);
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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean hasError = false;

		for (RangeWrapper r : rangeWrappers) {
			if (!r.valid()) {
				errors.add(new CompilerError("Error: First element of Range must be less or equal to second "
					+ "element in line " + r.a.beginLine));
				hasError = true;
			}
		}

		boolean errorInAdditionalRange = additionsRange.stream()
				.anyMatch(addition -> addition.semantischeAnalyse(tabelle, errors).isError());
		if (errorInAdditionalRange) {
			hasError = true;
		}

		for (Token i : additionsIdentifier) {
			String identifier = i.image;
			DeclNode temp = tabelle.find(identifier);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + identifier + " is not defined in line: "
						+ i.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.rangeT)) {
				errors.add(new CompilerError("Error: " + identifier + " is not a Range in line: "
						+ i.beginLine));
				hasError = true;
			}
		}

		return hasError ? VariableType.errorT : VariableType.rangeT;
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		Range range = null; // can not be initialized here because otherwise the add(...) method does not work
		for (RangeWrapper r : rangeWrappers) {
			if (r.b == null) {
				// char encoded as 'c'
				if (range == null) {
					range = new Range(r.a.image.charAt(1));
				} else {
					range.add(r.a.image.charAt(1));
				}
			} else {
				if (range == null) {
					range = new Range(r.a.image.charAt(1), r.b.image.charAt(1));
				} else {
					range.add(r.a.image.charAt(1), r.b.image.charAt(1));
				}
			}
		}

		for (RangeLitNode rln : additionsRange) {
			Range r = rln.run(tabelle).r;
			for (BasicRange br : r.getElement()) {
				if (range == null) {
					range = new Range(br.getStart(), br.getEnd());
				} else {
					range.add(br.getStart(), br.getEnd());
				}
			}
		}

		for (Token identifier : additionsIdentifier) {
			Range r = tabelle.find(identifier.image).value.r;
			for (BasicRange br : r.getElement()) {
				if (range == null) {
					range = new Range(br.getStart(), br.getEnd());
				} else {
					range.add(br.getStart(), br.getEnd());
				}
			}
		}

		return new Value(range);
	}
}
