package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.dto.TaskDto;
import mg.itu.taskmanagerspringws.dto.TaskScoreDto;
import mg.itu.taskmanagerspringws.dto.TaskTagDto;
import mg.itu.taskmanagerspringws.enums.Status;
import mg.itu.taskmanagerspringws.mapper.TaskMapper;
import mg.itu.taskmanagerspringws.model.Tag;
import mg.itu.taskmanagerspringws.model.Task;
import mg.itu.taskmanagerspringws.repository.TaskRepository;
import mg.itu.taskmanagerspringws.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskTagService taskTagService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskTagService taskTagService, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskTagService = taskTagService;
        this.taskMapper = taskMapper;
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return taskMapper.toDto(task);
    }

    public List<TaskDto> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto createTask(TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task saved = taskRepository.save(task);
        return taskMapper.toDto(saved);
    }

    public TaskDto updateTask(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        task.setDeadline(dto.getDeadline());
        task.setCompletedAt(dto.getCompletedAt());
        task.setProject(taskMapper.map(dto.getProjectId()));
        Task updated = taskRepository.save(task);
        return taskMapper.toDto(updated);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public int calculatePriorityScore(Task task) {
        if (task.getStatus() == Status.DONE) {
            return 0;
        }

        int score = 0;

        if (task.getDeadline() != null) {
            long daysLeft = LocalDate.now().until(task.getDeadline()).getDays();

            if (daysLeft <= 0) {
                score += 50; // en retard ou aujourd'hui
            } else if (daysLeft <= 1) {
                score += 40;
            } else if (daysLeft <= 3) {
                score += 30;
            } else if (daysLeft <= 7) {
                score += 20;
            } else {
                score += 10;
            }
        }

        switch (task.getStatus()) {
            case TODO:
                score += 30;
                break;
            case DOING:
                score += 20;
                break;
            case DONE:
                break;
        }

        if (task.getPriority() != null) {
            switch (task.getPriority()) {
                case HIGH:
                    score += 20;
                    break;
                case MEDIUM:
                    score += 10;
                    break;
                case LOW:
                    break;
            }
        }
        return score;
    }

    public List<TaskScoreDto> getTasksByProjectsOrdered(List<Long> projectIds) {
        List<Task> tasks = taskRepository.findAll()
                .stream()
                .filter(task -> projectIds.contains(task.getProject().getId()))
                .collect(Collectors.toList());

        tasks.sort(Comparator.comparingInt(this::calculatePriorityScore).reversed());

        return tasks.stream()
                .map(task -> {
                    TaskScoreDto dto = taskMapper.toTaskScoreDto(task);
                    dto.setPriorityScore(calculatePriorityScore(task));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksWithFilters(String status, String priority, String project,
                                          LocalDate startDeadline, LocalDate endDeadline) {

        Specification<Task> spec = Specification
                .where(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.hasPriority(priority))
                .and(TaskSpecification.hasProject(project))
                .and(TaskSpecification.deadlineBetween(startDeadline, endDeadline));

        return taskRepository.findAll(spec)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TagDto> getTaskTag(Long taskId) {
        return taskTagService.getTagsByTask(taskId);
    }

    public void addTagToTask(Long taskId, Long tagId) {
        taskTagService.addTagToTask(taskId, tagId);
    }

    public void removeTagFromTask(Long taskId, Long tagId) {
        taskTagService.removeTagFromTask(taskId, tagId);
    }
}