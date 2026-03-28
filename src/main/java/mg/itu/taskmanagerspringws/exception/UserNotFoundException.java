package mg.itu.taskmanagerspringws.exception;

public class UserNotFoundException extends AuthenticationRuntimeException {
    public UserNotFoundException() {
        super("Utilisateur non trouvé");
    }
}
