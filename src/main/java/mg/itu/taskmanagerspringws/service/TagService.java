package mg.itu.taskmanagerspringws.service;

import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.mapper.TagMapper;
import mg.itu.taskmanagerspringws.model.Tag;
import mg.itu.taskmanagerspringws.model.User;
import mg.itu.taskmanagerspringws.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final AuthService authService;

    @Autowired
    public TagService(TagRepository tagRepository, TagMapper tagMapper, AuthService authService) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.authService = authService;
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    public TagDto getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return tagMapper.toDto(tag);
    }

    public TagDto createTag(TagDto dto) {
        Tag tag = tagMapper.toEntity(dto);
        User user = new User();
        user.setId(authService.getCurrentUserId());
        tag.setUser(user);
        Tag saved = tagRepository.save(tag);
        return tagMapper.toDto(saved);
    }

    public TagDto updateTag(Long id, TagDto dto) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        tag.setName(dto.getName());

        Tag updated = tagRepository.save(tag);
        return tagMapper.toDto(updated);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public List<TagDto> getTagsByCurrentUser() {
        Long currentUserId = authService.getCurrentUserId();

        return tagRepository.findByUserId(currentUserId)
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }
}