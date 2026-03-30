package mg.itu.taskmanagerspringws.repository;

import mg.itu.taskmanagerspringws.dto.DashboardProjectDto;
import mg.itu.taskmanagerspringws.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectsByUserId(Long userId);
    @Query("""
    SELECT new mg.itu.taskmanagerspringws.dto.DashboardProjectDto(
        p.id,
        p.title,
        COUNT(t),
        SUM(CASE WHEN t.status = 'DONE' THEN 1 ELSE 0 END),
        SUM(CASE WHEN t.status = 'DOING' THEN 1 ELSE 0 END),
        SUM(CASE WHEN t.status = 'TODO' THEN 1 ELSE 0 END)
    )
    FROM Project p LEFT JOIN p.tasks t
    WHERE p.user.id = :userId
    GROUP BY p.id, p.title
    """)
    List<DashboardProjectDto> getProjectDashboardByUserId(@Param("userId") Long userId);
    long count();
}
