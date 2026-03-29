package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.ProjectDto;
import mg.itu.taskmanagerspringws.exception.EntityNotFoundException;
import mg.itu.taskmanagerspringws.exception.UserNotFoundException;
import mg.itu.taskmanagerspringws.mapper.ProjectMapper;
import mg.itu.taskmanagerspringws.model.Project;
import mg.itu.taskmanagerspringws.model.User;
import mg.itu.taskmanagerspringws.repository.ProjectRepository;
import mg.itu.taskmanagerspringws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {
    private final AuthService authService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(AuthService authService, ProjectRepository projectRepository,
                          ProjectMapper projectMapper) {
        this.authService = authService;
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectDto createProject(ProjectDto dto) {
        Project project = projectMapper.toEntity(dto);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    public List<ProjectDto> getProjectsByCurrentUser() {
        Long currentUserId = authService.getCurrentUserId();
        return projectRepository.findProjectsByUserId(currentUserId)
                .stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }


    public ProjectDto getProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        return projectMapper.toDto(project);
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProjectDto updateProject(Long id, ProjectDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());

        Project updatedProject = projectRepository.save(project);
        return projectMapper.toDto(updatedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Project not found");
        }
        projectRepository.deleteById(id);
    }
}