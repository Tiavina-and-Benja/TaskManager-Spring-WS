package mg.itu.taskmanagerspringws.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private Long userId;
}