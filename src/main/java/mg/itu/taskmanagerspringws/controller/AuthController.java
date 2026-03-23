package mg.itu.taskmanagerspringws.controller;

import mg.itu.taskmanagerspringws.dto.LoginDTO;
import mg.itu.taskmanagerspringws.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO.getEmail(), loginDTO.getPassword());
        if (token == null ) {
            return Map.of("message", "invalid login");
        }
        return Map.of("token", token);
    }
    
}
