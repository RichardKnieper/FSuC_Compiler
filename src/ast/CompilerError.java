package ast;

/**
 * Represents an error that is found during semantic analysis.
 */
public class  CompilerError extends Exception {
	String message;
    public CompilerError(String msg) {
        message = msg;
    }

    @Override
    public String toString(){
        return message;
    }
}
