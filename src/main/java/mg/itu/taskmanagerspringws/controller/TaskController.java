package mg.itu.taskmanagerspringws.controller;

import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.dto.TaskDto;
import mg.itu.taskmanagerspringws.dto.TaskScoreDto;
import mg.itu.taskmanagerspringws.dto.TaskTagDto;
import mg.itu.taskmanagerspringws.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.updateTask(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tag")
    public ResponseEntity<List<TagDto>> getTaskTags(@PathVariable Long id) {
        List<TagDto> tags = taskService.getTaskTag(id);
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/{id}/tag")
    public ResponseEntity<?> addTagToTask(@PathVariable Long id, @RequestBody Long tagId) {
        taskService.addTagToTask(id, tagId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/tag")
    public ResponseEntity<?> removeTagToTask(@PathVariable Long id, @RequestBody Long tagId) {
        taskService.removeTagFromTask(id, tagId);
        return ResponseEntity.noContent().build();
    }
}