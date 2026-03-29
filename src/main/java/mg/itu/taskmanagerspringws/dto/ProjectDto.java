package mg.itu.taskmanagerspringws.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProjectDto {
    private Long id;
    @NotEmpty
    private String title;
    private String description;
    @NotEmpty
    private Long userId;

}
