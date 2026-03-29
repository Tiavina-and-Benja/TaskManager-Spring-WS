package mg.itu.taskmanagerspringws.controller;

import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.dto.TaskDto;
import mg.itu.taskmanagerspringws.dto.TaskTagDto;
import mg.itu.taskmanagerspringws.service.TaskTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskTagController {

    private final TaskTagService taskTagService;

    public TaskTagController(TaskTagService taskTagService) {
        this.taskTagService = taskTagService;
    }

    @GetMapping("/tasks/{taskId}/tags")
    public ResponseEntity<List<TagDto>> getTagsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskTagService.getTagsByTask(taskId));
    }

    @GetMapping("/tasks/tag/{tagId}")
    public ResponseEntity<List<TaskDto>> getTasksByTag(@PathVariable Long tagId) {
        return ResponseEntity.ok(taskTagService.getTasksByTag(tagId));
    }

    @GetMapping("/tasks/tag/{tagId}/project/{projectId}")
    public ResponseEntity<List<TaskDto>> getTasksByTagAndProject(
            @PathVariable Long tagId,
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                taskTagService.getTasksByTagAndProject(tagId, projectId)
        );
    }

    @PostMapping("/task-tags")
    public ResponseEntity<Void> addTagToTask(@Valid @RequestBody TaskTagDto dto) {
        taskTagService.addTagToTask(dto.getTaskId(), dto.getTagId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/task-tags/{id}")
    public ResponseEntity<Void> removeTagFromTask(@PathVariable Long id) {
        taskTagService.removeTagFromTask(id);
        return ResponseEntity.noContent().build();
}
}