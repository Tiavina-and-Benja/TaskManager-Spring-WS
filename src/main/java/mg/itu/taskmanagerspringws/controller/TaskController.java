package mg.itu.taskmanagerspringws.controller;

import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.*;
import mg.itu.taskmanagerspringws.service.TaskService;
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
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TaskDto>> getTaskById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);

        EntityModel<TaskDto> model = EntityModel.of(task,
                linkTo(methodOn(TaskController.class).getTaskById(id)).withSelfRel(),
                linkTo(methodOn(TaskController.class).getTaskTags(id)).withRel("tags"),
                linkTo(methodOn(TaskController.class).getTaskHistoryByTaskId(id)).withRel("history"),
                linkTo(methodOn(TaskController.class).updateTask(id, null)).withRel("update"),
                linkTo(methodOn(TaskController.class).deleteTask(id)).withRel("delete")
        );

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TaskDto>> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto dto) {
        TaskDto updatedTask = taskService.updateTask(id, dto);

        EntityModel<TaskDto> model = EntityModel.of(updatedTask,
                linkTo(methodOn(TaskController.class).getTaskById(id)).withSelfRel(),
                linkTo(methodOn(TaskController.class).getTaskTags(id)).withRel("tags"),
                linkTo(methodOn(TaskController.class).getTaskHistoryByTaskId(id)).withRel("history"),
                linkTo(methodOn(TaskController.class).deleteTask(id)).withRel("delete")
        );

        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<String>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        EntityModel<String> model = EntityModel.of("Task deleted",
                linkTo(methodOn(TaskController.class).getTaskById(id)).withRel("self"), // optionnel, juste pour montrer
                linkTo(methodOn(TaskController.class).getTaskHistoryByTaskId(id)).withRel("history")
        );

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/tag")
    public ResponseEntity<CollectionModel<TagDto>> getTaskTags(@PathVariable Long id) {
        List<TagDto> tags = taskService.getTaskTag(id);

        CollectionModel<TagDto> collection = CollectionModel.of(tags,
                linkTo(methodOn(TaskController.class).getTaskById(id)).withRel("task"),
                linkTo(methodOn(TaskController.class).addTagToTask(id, null)).withRel("add-tag")
        );

        return ResponseEntity.ok(collection);
    }

    @PostMapping("/{id}/tag")
    public ResponseEntity<EntityModel<TagDto>> addTagToTask(@PathVariable Long id, @RequestBody Long tagId) {
        TagDto tag = taskService.addTagToTask(id, tagId);

        EntityModel<TagDto> model = EntityModel.of(tag,
                linkTo(methodOn(TaskController.class).getTaskTags(id)).withRel("tags"),
                linkTo(methodOn(TaskController.class).getTaskById(id)).withRel("task"),
                linkTo(methodOn(TaskController.class).removeTagToTask(id, tagId)).withRel("remove-tag")
        );

        return ResponseEntity.created(linkTo(methodOn(TaskController.class).getTaskTags(id)).toUri()).body(model);
    }

    @DeleteMapping("/{id}/tag")
    public ResponseEntity<EntityModel<String>> removeTagToTask(@PathVariable Long id, @RequestBody Long tagId) {
        taskService.removeTagFromTask(id, tagId);
        EntityModel<String> model = EntityModel.of("Tag removed",
                linkTo(methodOn(TaskController.class).getTaskTags(id)).withRel("tags"),
                linkTo(methodOn(TaskController.class).getTaskById(id)).withRel("task"),
                linkTo(methodOn(TaskController.class).addTagToTask(id, null)).withRel("add-tag")
        );

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<CollectionModel<EntityModel<TaskHistoryResponseDto>>> getTaskHistoryByTaskId(@PathVariable Long id) {

        List<EntityModel<TaskHistoryResponseDto>> histories = taskService.getTaskHistoryByTaskId(id)
                .stream()
                .map(history -> EntityModel.of(history,
                        linkTo(methodOn(TaskController.class).getTaskById(id)).withRel("task")
                ))
                .toList();

        CollectionModel<EntityModel<TaskHistoryResponseDto>> collection = CollectionModel.of(histories,
                linkTo(methodOn(TaskController.class).getTaskHistoryByTaskId(id)).withSelfRel(),
                linkTo(methodOn(TaskController.class).getTaskById(id)).withRel("task"),
                linkTo(methodOn(TaskController.class).getTaskTags(id)).withRel("tags")
        );

        return ResponseEntity.ok(collection);
    }

}