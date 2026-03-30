package mg.itu.taskmanagerspringws.dto;

import lombok.Data;
import mg.itu.taskmanagerspringws.enums.Role;

@Data
public class RegisterResponseDto {
    private String name;
    private String email;
    private Role role;
}
