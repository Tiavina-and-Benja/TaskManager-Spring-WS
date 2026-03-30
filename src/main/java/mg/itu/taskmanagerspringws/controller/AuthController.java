package mg.itu.taskmanagerspringws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.LoginDto;
import mg.itu.taskmanagerspringws.dto.RegisterRequestDto;
import mg.itu.taskmanagerspringws.dto.RegisterResponseDto;
import mg.itu.taskmanagerspringws.dto.UserDto;
import mg.itu.taskmanagerspringws.exception.AuthenticationRuntimeException;
import mg.itu.taskmanagerspringws.response.ApiResponse;
import mg.itu.taskmanagerspringws.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
     
    @Autowired
    AuthService authService;


    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<EntityModel<ApiResponse<?>>> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            String token = authService.login(loginDto);

            ApiResponse<?> response = new ApiResponse<>(true, "Authentification réussie", Map.of("token", token));
            EntityModel<ApiResponse<?>> model = EntityModel.of(response,
                    linkTo(methodOn(AuthController.class).login(loginDto)).withSelfRel(),
                    linkTo(methodOn(UserController.class).getCurrentUser()).withRel("me"),
                    linkTo(methodOn(ProjectController.class).getCurrentUserProjects()).withRel("next"),
                    linkTo(methodOn(AuthController.class).register(null)).withRel("register")
            );

            return ResponseEntity.ok(model);

        } catch (AuthenticationRuntimeException e) {
            ApiResponse<?> response = new ApiResponse<>(false, e.getMessage());
            EntityModel<ApiResponse<?>> model = EntityModel.of(response,
                    linkTo(methodOn(AuthController.class).login(loginDto)).withSelfRel(),
                    linkTo(methodOn(AuthController.class).register(null)).withRel("register")
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
        }
    }

    @Operation(summary = "Register")
    @PostMapping("/register")
    public ResponseEntity<EntityModel<RegisterResponseDto>> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto dto = authService.register(registerRequestDto);

        EntityModel<RegisterResponseDto> model = EntityModel.of(dto,
                linkTo(methodOn(AuthController.class).register(registerRequestDto)).withSelfRel(),
                linkTo(methodOn(AuthController.class).login(null)).withRel("login")
        );

        return ResponseEntity.status(201).body(model);
    }
    
}
