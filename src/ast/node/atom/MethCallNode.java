package ast.node.atom;

import ast.CompilerError;
import ast.ParamWrapper;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.decl.DeclNode;
import ast.node.decl.MethDeclNode;
import ast.node.decl.VarDeclNode;
import ast.node.type.TypeNode;
import ast.value.Value;
import jj.Token;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a method call. identifier is the name of the method and elementList contains the parameters of the
 * method call.
 */
public class MethCallNode extends AtomNode {
	public Token identifier;
	public List<AtomNode> elementList = new LinkedList<>();

	public MethCallNode() {
	}

	public String toString(String indent) {
		String res = indent + "MethCallNode" + identifier.image + "\n";
		for (AtomNode node : elementList)
			res += "\n" + node.toString(indent + "\t");
		return res;
	}

	public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
		DeclNode methodDeclNode = tabelle.find(identifier.image);
		if (methodDeclNode == null) {
			errors.add(new CompilerError("Error: There exists no method with the name " + identifier.image
				+ " in the line " + identifier.beginLine));
			return VariableType.errorT;
		}

		if (methodDeclNode instanceof MethDeclNode) {
			List<VariableType> neededParams = ((MethDeclNode) methodDeclNode).params
					.values()
					.stream()
					.sorted(Comparator.comparingInt(ParamWrapper::getIndex))
					.map(paramWrapper -> paramWrapper.getType().variableType)
					.collect(Collectors.toList());

			List<VariableType> elementParams = elementList.stream()
					.map(atomNode -> atomNode.semantischeAnalyse(tabelle, errors))
					.collect(Collectors.toList());

			if (neededParams.size() != elementParams.size()) {
				errors.add(new CompilerError("Error: The number of parameters in the method call does not match the "
						+ " number of parameters in the method decleration in the line " + identifier.beginLine));
				return VariableType.errorT;
			}

			if (elementParams.contains(VariableType.errorT)) {
				return VariableType.errorT;
			}

			for (int i = 0; i < neededParams.size(); i++) {
				if (!elementParams.get(i).hasSameTypeAs(neededParams.get(i))) {
					errors.add(new CompilerError("Error: Parameter " + i + " in method call in line " + identifier.beginLine
						+ " should be " + neededParams.get(i) + " but is " + elementParams.get(i)));
					return VariableType.errorT;
				}
			}
		} else {
			errors.add(new CompilerError("Error: " + identifier.image + " is not a method in line " + identifier.beginLine));
			return VariableType.errorT;
		}

		return methodDeclNode.type.variableType;
	}

	@Override
	public Value run(SymbolTabelle tabelle) {
		MethDeclNode methDecl = (MethDeclNode) tabelle.find(identifier.image);
		SymbolTabelle neuTabelle = new SymbolTabelle(tabelle);
		methDecl.params
				.values()
				.stream()
				.sorted(Comparator.comparingInt(ParamWrapper::getIndex))
				.forEach(wrapper -> {
					Value v = elementList.get(wrapper.getIndex()).run(tabelle);
					VarDeclNode node = new VarDeclNode(new TypeNode(v.type), wrapper.getIdentifier(), null);
					node.value = v;
					neuTabelle.add(wrapper.getIdentifier().image, node);
				});
		return methDecl.body.run(neuTabelle);
	}
}
