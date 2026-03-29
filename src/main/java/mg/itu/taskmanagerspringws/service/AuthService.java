package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.LoginDto;
import mg.itu.taskmanagerspringws.dto.RegisterRequestDto;
import mg.itu.taskmanagerspringws.dto.RegisterResponseDto;
import mg.itu.taskmanagerspringws.dto.UserDto;
import mg.itu.taskmanagerspringws.exception.EmailAlreadyUsedException;
import mg.itu.taskmanagerspringws.exception.InvalidPasswordException;
import mg.itu.taskmanagerspringws.exception.UserNotFoundException;
import mg.itu.taskmanagerspringws.mapper.UserMapper;
import mg.itu.taskmanagerspringws.model.User;
import mg.itu.taskmanagerspringws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {
    UserRepository repo;
    JwtService jwtService;
    UserMapper userMapper;

    @Autowired
    public AuthService(
            UserRepository userRepository,
            JwtService jwtService,
            UserMapper userMapper
    ) {
        this.repo = userRepository;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    public String login(LoginDto loginDto) {
        User user = repo.findByEmail(loginDto.getEmail())
                .orElseThrow(UserNotFoundException::new);
        if (!Objects.equals(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return jwtService.generateToken(user);
    }

    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        if (repo.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }
        User user = userMapper.registerRequestDtoToUser(registerRequestDto);
        User savedUser = repo.save(user);
        return userMapper.userToRegisterResponseDto(savedUser);
    }

    public Long getCurrentUserId() {
        return (Long)
                Objects.requireNonNull(SecurityContextHolder.getContext()
                                .getAuthentication())
                        .getPrincipal();
    }

    public UserDto getCurrentUser() {
        Long userId = getCurrentUserId();
        User user = repo.getById(userId);
        return userMapper.toDto(user);
    }
}
