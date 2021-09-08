package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;
import ast.node.decl.DeclNode;
import jj.Token;

import java.util.LinkedList;
import java.util.List;

public class RangeLitNode extends AtomNode {
	public List<Range> ranges = new LinkedList<>();

	// for Range + Range
	public List<RangeLitNode> additionsRange = new LinkedList<>();
	public List<Token> additionsIdentifier = new LinkedList<>();

	public void add(Token a, Token b) {
		ranges.add(new Range(a, b));
	}

	public void add(Token i) {
		additionsIdentifier.add(i);
	}

	public void add(RangeLitNode r) {
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

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		boolean hasError = false;

		boolean errorInAdditionalRange = additionsRange.stream()
				.anyMatch(addition -> addition.semantischeAnalyse(tabelle, errors).isError());
		if (errorInAdditionalRange) {
			hasError = true;
		}

		for (Token i : additionsIdentifier) {
			String identifier = i.image;
			DeclNode temp = tabelle.find(identifier);
			if (temp == null) {
				errors.add(new CompilerError("Error: " + identifier + "is not defined in line: "
						+ i.beginLine));
				hasError = true;
			} else if (!temp.type.variableType.hasSameTypeAs(VariableType.rangeT)) {
				errors.add(new CompilerError("Error: " + identifier + "is not a Range in line: "
						+ i.beginLine));
				hasError = true;
			}
		}

		return hasError ? VariableType.errorT : VariableType.rangeT;
	}
}
