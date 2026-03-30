package mg.itu.taskmanagerspringws.controller;

import mg.itu.taskmanagerspringws.dto.ProjectDto;
import mg.itu.taskmanagerspringws.dto.UserDto;
import mg.itu.taskmanagerspringws.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<List<ProjectDto>> getUsersProject(@PathVariable Long id) {
        List<ProjectDto> projects = this.userService.getUserProjects(id);
        return ResponseEntity.ok(projects);
    }
}
