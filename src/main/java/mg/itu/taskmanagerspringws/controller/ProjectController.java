package mg.itu.taskmanagerspringws.controller;

import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.DashboardProjectDto;
import mg.itu.taskmanagerspringws.dto.ProjectDto;
import mg.itu.taskmanagerspringws.dto.TaskDto;
import mg.itu.taskmanagerspringws.dto.TaskScoreDto;
import mg.itu.taskmanagerspringws.service.ProjectService;
import mg.itu.taskmanagerspringws.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<DashboardProjectDto>> getDashboardProjects() {
        return ResponseEntity.ok(projectService.getDashboardProjects());
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProjectDto>> getCurrentUserProjects() {
        return ResponseEntity.ok(projectService.getProjectsByCurrentUser());
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto dto) {
        ProjectDto createdProject = projectService.createProject(dto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Long id) {
        ProjectDto project = projectService.getProject(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id,@Valid @RequestBody ProjectDto dto) {
        ProjectDto updatedProject = projectService.updateProject(id, dto);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDto>> getProjectTasks(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDeadline,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDeadline) {
        List<TaskDto> tasks = projectService.getProjectTasks(id, status, priority, startDeadline, endDeadline);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<TaskDto> addTaskToProjects(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        TaskDto task = projectService.addTasksToProject(id, taskDto);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/tasks/prioritized")
    public ResponseEntity<List<TaskScoreDto>> getOrderedPrioritizedTasks(@PathVariable Long id) {
        List<TaskScoreDto> taskScore = projectService.getOrderedPrioritizedTasks(id);
        return ResponseEntity.ok(taskScore);
    }
}
