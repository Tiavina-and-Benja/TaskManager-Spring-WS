package mg.itu.taskmanagerspringws.controller;

import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.LoginDto;
import mg.itu.taskmanagerspringws.dto.RegisterRequestDto;
import mg.itu.taskmanagerspringws.dto.RegisterResponseDto;
import mg.itu.taskmanagerspringws.exception.AuthenticationRuntimeException;
import mg.itu.taskmanagerspringws.response.ApiResponse;
import mg.itu.taskmanagerspringws.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
     
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            String token = authService.login(loginDto);
            System.out.println("Authentication en cours: " + token);
            return ResponseEntity
                    .ok(new ApiResponse<>(true, "Authentification réussi", Map.of("token", token)));
        } catch (AuthenticationRuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto dto = authService.register(registerRequestDto);
        return ResponseEntity.status(201).body(dto) ;
    }
    
}
