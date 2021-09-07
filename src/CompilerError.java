
public class CompilerError extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
    public CompilerError(String msg) {
        message = msg;
    }
    @Override
    public String toString(){
        return message;
    }
}
