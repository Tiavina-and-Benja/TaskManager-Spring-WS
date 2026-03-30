package mg.itu.taskmanagerspringws.dto;

import lombok.Data;
import mg.itu.taskmanagerspringws.enums.Role;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private Role role;
}
