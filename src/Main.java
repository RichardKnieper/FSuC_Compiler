import ast.VariableType;
import ast.node.CuNode;
import ast.value.Value;
import jj.ParseException;
import jj.Parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        if (args.length <= 0) {
            System.out.println("You need to specify a file.");
            return;
        }
        FileReader fr;
        try {
            fr = new FileReader(args[0]);
        } catch (FileNotFoundException fnf) {
            System.out.println("File not found.");
            return;
        }

        Parser parser = new Parser(fr);
        try {
            CuNode ast = parser.cu();
            VariableType returnType = ast.semantischeAnalyse();
            if (!returnType.isError()) {
                try {
                    Value result = ast.run();
                    System.out.println(result.fa.toString());
                } catch (RuntimeException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (ParseException exception) {
            System.out.println("The syntax has errors.");
            exception.printStackTrace();
        }
    }
}
