package mg.itu.taskmanagerspringws.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("Email déjà utilisé");
    }
}
