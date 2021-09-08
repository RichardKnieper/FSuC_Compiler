package ast.node.atom.literals;

import ast.CompilerError;
import ast.SymbolTabelle;
import ast.VariableType;
import ast.node.atom.AtomNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapLitNode extends AtomNode {
    public Map<AtomNode, AtomNode> elements = new HashMap<>();

    public String toString(String indent) {
        String res = indent + "MapLitNode";
        for (Map.Entry<AtomNode, AtomNode> me : elements.entrySet()) {
            res = me.getKey().toString(indent + "\t") + me.getValue().toString(indent + "\t") + "\n";
        }
        return res;
    }

    @Override
    public VariableType semantischeAnalyse(SymbolTabelle tabelle, List<CompilerError> errors) {
        VariableType keyTypes = elements.keySet()
                .stream()
                .map(atom -> atom.semantischeAnalyse(tabelle, errors))
                .reduce((a, b) -> a.hasSameTypeAs(b) ? a : VariableType.errorT)
                .get();

        VariableType valueTypes = elements.values()
                .stream()
                .map(atom -> atom.semantischeAnalyse(tabelle, errors))
                .reduce((a, b) -> a.hasSameTypeAs(b) ? a : VariableType.errorT)
                .get();

        if (keyTypes.isError()) {
            errors.add(new CompilerError("Error: not all key types of map match."));
        }

        if (valueTypes.isError()) {
            errors.add(new CompilerError("Error: not all value types of map match."));
        }

        if (keyTypes.isError() || valueTypes.isError()) {
            return VariableType.errorT;
        } else {
            return new VariableType.MapVariableType(keyTypes, valueTypes);
        }
    }
}
