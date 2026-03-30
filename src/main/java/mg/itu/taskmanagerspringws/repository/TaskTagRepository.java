package mg.itu.taskmanagerspringws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.taskmanagerspringws.model.TaskTag;

import java.util.List;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {

    List<TaskTag> findByTaskId(Long taskId);

    List<TaskTag> findByTagId(Long tagId);

    List<TaskTag> findByTagIdAndTask_ProjectId(Long tagId, Long projectId);

    boolean existsByTaskIdAndTagId(Long taskId, Long tagId);

    void deleteByTask_IdAndTag_Id(Long taskId, Long tagId);
}