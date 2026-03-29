package mg.itu.taskmanagerspringws.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTagDto {

    private Long taskId;
    private Long tagId;
}