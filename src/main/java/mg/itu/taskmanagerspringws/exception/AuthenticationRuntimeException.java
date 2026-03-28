package mg.itu.taskmanagerspringws.exception;

public class AuthenticationRuntimeException extends RuntimeException {
    public AuthenticationRuntimeException(String message) {
        super(message);
    }
}
