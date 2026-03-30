package mg.itu.taskmanagerspringws.controller;

import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.*;
import mg.itu.taskmanagerspringws.service.ProjectService;
import mg.itu.taskmanagerspringws.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<CollectionModel<EntityModel<DashboardProjectDto>>> getDashboardProjects() {

        List<EntityModel<DashboardProjectDto>> projects = projectService.getDashboardProjects()
                .stream()
                .map(project -> EntityModel.of(project,
                        linkTo(methodOn(ProjectController.class).getProject(project.getProjectId())).withSelfRel(),
                        linkTo(methodOn(ProjectController.class).getProjectTasks(project.getProjectId(), null, null, null, null)).withRel("tasks"),
                        linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("my-projects")
                ))
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(projects,
                        linkTo(methodOn(ProjectController.class).getDashboardProjects()).withSelfRel()
                )
        );
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProjectDto>>> getCurrentUserProjects() {

        List<EntityModel<ProjectDto>> projects = projectService.getProjectsByCurrentUser()
                .stream()
                .map(project -> EntityModel.of(project,
                        linkTo(methodOn(ProjectController.class).getProject(project.getId())).withSelfRel(),
                        linkTo(methodOn(ProjectController.class).getProjectTasks(project.getId(), null, null, null, null)).withRel("tasks"),
                        linkTo(methodOn(ProjectController.class).getDashboardProjects()).withRel("dashboard")
                ))
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(projects,
                        linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withSelfRel()
                )
        );
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProjectDto>> createProject(@Valid @RequestBody ProjectDto dto) {

        ProjectDto createdProject = projectService.createProject(dto);

        EntityModel<ProjectDto> model = EntityModel.of(createdProject,
                linkTo(methodOn(ProjectController.class).getProject(createdProject.getId())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getProjectTasks(createdProject.getId(), null, null, null, null)).withRel("tasks"),
                linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("my-projects"),
                linkTo(methodOn(ProjectController.class).getDashboardProjects()).withRel("dashboard")
        );

        return ResponseEntity
                .created(linkTo(methodOn(ProjectController.class)
                        .getProject(createdProject.getId())).toUri())
                .body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProjectDto>> getProject(@PathVariable Long id) {

        ProjectDto project = projectService.getProject(id);

        EntityModel<ProjectDto> model = EntityModel.of(project,
                linkTo(methodOn(ProjectController.class).getProject(id)).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getProjectTasks(id, null, null, null, null)).withRel("tasks"),
                linkTo(methodOn(ProjectController.class).addTaskToProjects(id, null)).withRel("add-task"),
                linkTo(methodOn(ProjectController.class).getTaskHistoryByProjectId(id)).withRel("history"),
                linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("my-projects"),
                linkTo(methodOn(ProjectController.class).getDashboardProjects()).withRel("dashboard"),
                linkTo(methodOn(ProjectController.class).updateProject(id, null)).withRel("update"),
                linkTo(methodOn(ProjectController.class).deleteProject(id)).withRel("delete")
        );

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProjectDto>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDto dto) {

        ProjectDto updatedProject = projectService.updateProject(id, dto);

        EntityModel<ProjectDto> model = EntityModel.of(updatedProject,
                linkTo(methodOn(ProjectController.class).getProject(id)).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getProjectTasks(id, null, null, null, null)).withRel("tasks"),
                linkTo(methodOn(ProjectController.class).addTaskToProjects(id, null)).withRel("add-task"),
                linkTo(methodOn(ProjectController.class).getTaskHistoryByProjectId(id)).withRel("history"),
                linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("my-projects"),
                linkTo(methodOn(ProjectController.class).getDashboardProjects()).withRel("dashboard"),
                linkTo(methodOn(ProjectController.class).deleteProject(id)).withRel("delete")
        );

        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        projectService.deleteProject(id);

        return ResponseEntity.noContent()
                .location(linkTo(methodOn(ProjectController.class)
                        .getCurrentUserProjects()).toUri())
                .build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<CollectionModel<EntityModel<TaskDto>>> getProjectTasks(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDeadline,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDeadline) {

        List<EntityModel<TaskDto>> tasks = projectService
                .getProjectTasks(id, status, priority, startDeadline, endDeadline)
                .stream()
                .map(task -> EntityModel.of(task,
                        linkTo(methodOn(ProjectController.class).getProject(id)).withRel("project")
                ))
                .toList();

        CollectionModel<EntityModel<TaskDto>> collection = CollectionModel.of(tasks,
                linkTo(methodOn(ProjectController.class)
                        .getProjectTasks(id, status, priority, startDeadline, endDeadline))
                        .withSelfRel(),
                linkTo(methodOn(ProjectController.class)
                        .getProject(id)).withRel("project"),
                linkTo(methodOn(ProjectController.class)
                        .addTaskToProjects(id, null)).withRel("add-task")
        );

        return ResponseEntity.ok(collection);
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<EntityModel<TaskDto>> addTaskToProjects(
            @PathVariable Long id,
            @Valid @RequestBody TaskDto taskDto) {

        TaskDto task = projectService.addTasksToProject(id, taskDto);

        EntityModel<TaskDto> model = EntityModel.of(task,
                linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getProject(id)).withRel("project"),
                linkTo(methodOn(ProjectController.class).getProjectTasks(id, null, null, null, null)).withRel("tasks"),
                linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("my-projects"),
                linkTo(methodOn(ProjectController.class).getDashboardProjects()).withRel("dashboard")
        );

        return ResponseEntity
                .created(linkTo(methodOn(ProjectController.class).getProjectTasks(id, null, null, null, null)).toUri())
                .body(model);
    }

    @GetMapping("/{id}/tasks/prioritized")
    public ResponseEntity<CollectionModel<EntityModel<TaskScoreDto>>> getOrderedPrioritizedTasks(@PathVariable Long id) {

        List<EntityModel<TaskScoreDto>> tasks = projectService.getOrderedPrioritizedTasks(id)
                .stream()
                .map(ts -> EntityModel.of(ts,
                        linkTo(methodOn(ProjectController.class).getProject(id)).withRel("project")
                ))
                .toList();

        CollectionModel<EntityModel<TaskScoreDto>> collection = CollectionModel.of(tasks,
                linkTo(methodOn(ProjectController.class).getOrderedPrioritizedTasks(id)).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getProjectTasks(id, null, null, null, null)).withRel("tasks"),
                linkTo(methodOn(ProjectController.class).addTaskToProjects(id, null)).withRel("add-task"),
                linkTo(methodOn(ProjectController.class).getProject(id)).withRel("project"),
                linkTo(methodOn(ProjectController.class).getDashboardProjects()).withRel("dashboard")
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<CollectionModel<EntityModel<TaskHistoryResponseDto>>> getTaskHistoryByProjectId(@PathVariable Long id) {

        List<EntityModel<TaskHistoryResponseDto>> histories = projectService
                .getTaskHistoryByProjectId(id)
                .stream()
                .map(history -> EntityModel.of(history,
                        linkTo(methodOn(ProjectController.class).getProject(id)).withRel("project")
                ))
                .toList();

        CollectionModel<EntityModel<TaskHistoryResponseDto>> collection = CollectionModel.of(histories,
                linkTo(methodOn(ProjectController.class).getTaskHistoryByProjectId(id)).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getProject(id)).withRel("project"),
                linkTo(methodOn(ProjectController.class).getProjectTasks(id, null, null, null, null)).withRel("tasks"),
                linkTo(methodOn(ProjectController.class).getDashboardProjects()).withRel("dashboard"),
                linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("my-projects")
        );

        return ResponseEntity.ok(collection);
    }


    private EntityModel<ProjectDto> toModel(ProjectDto project) {
        return EntityModel.of(project,
                linkTo(methodOn(ProjectController.class).getProject(project.getId())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("my-projects"),
                linkTo(methodOn(ProjectController.class).getProjectTasks(project.getId(), null, null, null, null)).withRel("tasks")
        );
    }
}
