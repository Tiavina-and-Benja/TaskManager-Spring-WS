package mg.itu.taskmanagerspringws.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import mg.itu.taskmanagerspringws.enums.Role;

@Data
public class RegisterRequestDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private Role role = Role.USER;
}
