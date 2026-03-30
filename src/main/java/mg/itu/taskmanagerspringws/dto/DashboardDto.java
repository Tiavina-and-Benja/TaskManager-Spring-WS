package mg.itu.taskmanagerspringws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto {
    private Long totalProject;
    private Long totalUsers;
}
