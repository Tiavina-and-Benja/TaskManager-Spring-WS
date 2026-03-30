package mg.itu.taskmanagerspringws.controller;

import mg.itu.taskmanagerspringws.dto.ProjectDto;
import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.dto.UserDto;
import mg.itu.taskmanagerspringws.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        List<EntityModel<UserDto>> users = userService.getAllUsers()
                .stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).getUsersProject(user.getId())).withRel("projects")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<UserDto>> collection = CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        EntityModel<UserDto> model = EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsersProject(id)).withRel("projects"),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users")
        );
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<CollectionModel<ProjectDto>> getUsersProject(@PathVariable Long id) {
        List<ProjectDto> projects = userService.getUserProjects(id);

        CollectionModel<ProjectDto> collection = CollectionModel.of(projects,
                linkTo(methodOn(UserController.class).getUsersProject(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserById(id)).withRel("user"),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users")
        );

        return ResponseEntity.ok(collection);
    }
}
