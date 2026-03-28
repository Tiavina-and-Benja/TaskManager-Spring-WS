package mg.itu.taskmanagerspringws.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private boolean success = true;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
