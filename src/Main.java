import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        Parser parser = new Parser(new FileReader("src/example.fare"));
        parser.cu();
    }
}
