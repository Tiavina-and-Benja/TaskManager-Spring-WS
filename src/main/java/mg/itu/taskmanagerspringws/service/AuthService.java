package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.model.User;
import mg.itu.taskmanagerspringws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepository repo;
    @Autowired
    JwtService jwtService;

    public String login(String email, String password) {
        Optional<User> user = repo.findByEmailAndPassword(email, password);
        return user.map(value -> jwtService.generateToken(
                value.getEmail(),
                value.getRole().toString()
        )).orElse(null);
    }
}
