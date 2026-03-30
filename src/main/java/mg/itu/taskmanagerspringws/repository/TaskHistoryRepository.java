package mg.itu.taskmanagerspringws.repository;

import mg.itu.taskmanagerspringws.model.Task;
import mg.itu.taskmanagerspringws.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findByTaskId(Long taskId);
    List<TaskHistory> findByTaskProjectId(Long projectId);

    Iterable<TaskHistory> task(Task task);
}
