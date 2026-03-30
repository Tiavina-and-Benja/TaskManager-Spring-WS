package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.ProjectDto;
import mg.itu.taskmanagerspringws.dto.UserDto;
import mg.itu.taskmanagerspringws.exception.EntityNotFoundException;
import mg.itu.taskmanagerspringws.mapper.UserMapper;
import mg.itu.taskmanagerspringws.model.User;
import mg.itu.taskmanagerspringws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private ProjectService projectService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return userMapper.toDto(user);
    }

    public List<ProjectDto> getUserProjects(Long userId) {
        return this.projectService.getProjectsByUserId(userId);
    }
}
