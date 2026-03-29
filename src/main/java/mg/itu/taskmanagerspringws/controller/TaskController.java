package mg.itu.taskmanagerspringws.controller;

import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.TaskDto;
import mg.itu.taskmanagerspringws.dto.TaskScoreDto;
import mg.itu.taskmanagerspringws.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("")
    public ResponseEntity<List<TaskDto>> getTasksByProject(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String project,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDeadline,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDeadline
    ) {
        System.out.println(String.format(
                "Filters -> status: %s, priority: %s, project: %s, startDeadline: %s, endDeadline: %s",
                status, priority, project, startDeadline, endDeadline
        ));
        List<TaskDto> tasks = taskService.getTasksWithFilters(status, priority, project, startDeadline, endDeadline);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
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

    @GetMapping("/prioritized")
    public ResponseEntity<List<TaskScoreDto>> getTasksByProjectsOrdered(
            @RequestParam List<Long> projectIds
    ) {
        return ResponseEntity.ok(taskService.getTasksByProjectsOrdered(projectIds));
    }
}