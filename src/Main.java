import ast.VariableType;
import ast.node.CuNode;
import ast.value.Value;
import jj.ParseException;
import jj.Parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        Parser parser = new Parser(new FileReader("src/test_valid.fare"));
        CuNode ast = parser.cu();
        VariableType returnType = ast.semantischeAnalyse();
        if (!returnType.isError())  {
            Value result = ast.run();
            System.out.println(result);
        }
        int debug = 42; // TODO delete; this variable exists to stop the debugger to examine ast
    }
}
