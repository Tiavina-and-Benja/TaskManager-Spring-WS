package mg.itu.taskmanagerspringws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mg.itu.taskmanagerspringws.dto.ProjectDto;
import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.dto.UserDto;
import mg.itu.taskmanagerspringws.service.AuthService;
import mg.itu.taskmanagerspringws.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(summary = "Get current user", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public ResponseEntity<EntityModel<UserDto>> getCurrentUser() {
        UserDto user = authService.getCurrentUser();

        EntityModel<UserDto> model = EntityModel.of(user,
                linkTo(methodOn(UserController.class).getCurrentUser()).withSelfRel(),
                linkTo(methodOn(AuthController.class).login(null)).withRel("login"),
                linkTo(methodOn(AuthController.class).register(null)).withRel("register"),
                linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("projects")
        );

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Get All user", security = @SecurityRequirement(name = "bearerAuth"))
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

    @Operation(summary = "Get User By Id", security = @SecurityRequirement(name = "bearerAuth"))
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

    @Operation(summary = "Get user's projects", security = @SecurityRequirement(name = "bearerAuth"))
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
