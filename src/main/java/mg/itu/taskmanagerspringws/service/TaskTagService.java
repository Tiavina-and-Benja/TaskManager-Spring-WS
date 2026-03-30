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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskTagService {

    private final TaskTagRepository taskTagRepository;
    private final TagMapper tagMapper;
    private final TaskMapper taskMapper;
    private final TagService tagService;
    private final TaskService taskService;


    public TaskTagService(TaskTagRepository taskTagRepository,
                          TagMapper tagMapper,
                          TaskMapper taskMapper,
                          TagService tagService,
                          @Lazy TaskService taskService) {
        this.taskTagRepository = taskTagRepository;
        this.tagMapper = tagMapper;
        this.taskMapper = taskMapper;
        this.tagService = tagService;
        this.taskService = taskService;
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

    public TagDto addTagToTask(Long taskId, Long tagId) {

        if (taskTagRepository.existsByTaskIdAndTagId(taskId, tagId)) {
            throw new RuntimeException("Tag already assigned to task");
        }

        Task task = new Task();
        task.setId(taskId);
        TagDto tag = tagService.getTagById(tagId);

        TaskTag taskTag = new TaskTag();
        taskTag.setTask(task);
        taskTag.setTag(tagMapper.toEntity(tag));
        taskTag.setAssignedAt(LocalDate.now());

        taskTagRepository.save(taskTag);
        return tag;
    }

    public void removeTagFromTask(Long id) {
        taskTagRepository.deleteById(id);
    }

    public void removeTagFromTask(Long taskId, Long tagId) { taskTagRepository.deleteByTask_IdAndTag_Id(taskId, tagId);}
}