package mg.itu.taskmanagerspringws.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {
        super("Entity Not Found");
    }
}
