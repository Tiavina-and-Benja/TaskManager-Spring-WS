package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.dto.TaskDto;
import mg.itu.taskmanagerspringws.mapper.TagMapper;
import mg.itu.taskmanagerspringws.mapper.TaskMapper;
import mg.itu.taskmanagerspringws.model.Tag;
import mg.itu.taskmanagerspringws.model.Task;
import mg.itu.taskmanagerspringws.model.TaskTag;
import mg.itu.taskmanagerspringws.repository.TagRepository;
import mg.itu.taskmanagerspringws.repository.TaskRepository;
import mg.itu.taskmanagerspringws.repository.TaskTagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskTagService {

    private final TaskTagRepository taskTagRepository;
    private final TagMapper tagMapper;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;


    public TaskTagService(TaskTagRepository taskTagRepository,
                        TagMapper tagMapper,
                        TaskMapper taskMapper,
                        TaskRepository taskRepository,
                        TagRepository tagRepository) {
        this.taskTagRepository = taskTagRepository;
        this.tagMapper = tagMapper;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    public List<TagDto> getTagsByTask(Long taskId) {
        return taskTagRepository.findByTaskId(taskId)
                .stream()
                .map(tt -> tagMapper.toDto(tt.getTag()))
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByTag(Long tagId) {
        return taskTagRepository.findByTagId(tagId)
                .stream()
                .map(tt -> taskMapper.toDto(tt.getTask()))
                .collect(Collectors.toList());
    }

    public void addTagToTask(Long taskId, Long tagId) {

        if (taskTagRepository.existsByTaskIdAndTagId(taskId, tagId)) {
            throw new RuntimeException("Tag already assigned to task");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        TaskTag taskTag = new TaskTag();
        taskTag.setTask(task);
        taskTag.setTag(tag);
        taskTag.setAssignedAt(LocalDate.now());

        taskTagRepository.save(taskTag);
    }

    public void removeTagFromTask(Long id) {
        taskTagRepository.deleteById(id);
    }

    public void removeTagFromTask(Long taskId, Long tagId) { taskTagRepository.deleteByTask_IdAndTag_Id(taskId, tagId);}
}