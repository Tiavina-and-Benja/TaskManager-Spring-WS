package mg.itu.taskmanagerspringws.exception;

public class InvalidPasswordException extends AuthenticationRuntimeException {
    public InvalidPasswordException() {
        super("Mot de passe invalid");
    }
}
