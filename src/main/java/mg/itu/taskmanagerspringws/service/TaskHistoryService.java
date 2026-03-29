package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.TaskHistoryDto;
import mg.itu.taskmanagerspringws.dto.TaskHistoryResponseDto;
import mg.itu.taskmanagerspringws.mapper.TaskHistoryMapper;
import mg.itu.taskmanagerspringws.model.TaskHistory;
import mg.itu.taskmanagerspringws.repository.TaskHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryService {

    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskHistoryMapper taskHistoryMapper;

    @Autowired
    public TaskHistoryService(TaskHistoryRepository taskHistoryRepository, TaskHistoryMapper taskHistoryMapper) {
        this.taskHistoryRepository = taskHistoryRepository;
        this.taskHistoryMapper = taskHistoryMapper;
    }

    public TaskHistoryDto createTaskHistory(TaskHistoryDto dto) {
        TaskHistory taskHistory = this.taskHistoryRepository.save(taskHistoryMapper.toEntity(dto));
        return taskHistoryMapper.taskHistorytoDto(taskHistory);
    }

    public List<TaskHistoryResponseDto> getTaskHistoryByTaskId(Long taskId) {
        List<TaskHistory> taskHistories = this.taskHistoryRepository.findByTaskId(taskId);
        return taskHistories
                .stream()
                .map(taskHistoryMapper::toDto)
                .toList();
    }

    public List<TaskHistoryResponseDto> getTaskHistoryByProjectId(Long projectId) {
        List<TaskHistory> taskHistories = this.taskHistoryRepository.findByTaskProjectId(projectId);
        return taskHistories
                .stream()
                .map(taskHistoryMapper::toDto)
                .toList();
    }
}
