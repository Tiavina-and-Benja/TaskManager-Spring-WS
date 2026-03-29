package mg.itu.taskmanagerspringws.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectDto {
    private Long id;
    @NotEmpty
    private String title;
    private String description;
    @NotNull
    private Long userId;

}
