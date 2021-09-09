package ast.exceptions;

public class RuntimeError extends RuntimeException {
    String message;

    public RuntimeError(String message, String message1) {
        super(message);
        this.message = message1;
    }

    @Override
    public String toString() {
        return message;
    }
}
