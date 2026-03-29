package mg.itu.taskmanagerspringws.repository;

import mg.itu.taskmanagerspringws.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectsByUserId(Long userId);
}
