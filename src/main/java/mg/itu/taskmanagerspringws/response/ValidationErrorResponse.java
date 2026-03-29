package mg.itu.taskmanagerspringws.response;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationErrorResponse {
    private String message;
    private Map<String, String> errors;

    public ValidationErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

}