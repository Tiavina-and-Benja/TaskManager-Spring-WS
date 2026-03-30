package mg.itu.taskmanagerspringws.controller;

import jakarta.validation.Valid;
import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TagDto>> getTagById(@PathVariable Long id) {
        TagDto tag = tagService.getTagById(id);
        EntityModel<TagDto> model = EntityModel.of(tag,
                linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel(),
                linkTo(methodOn(TagController.class).getMyTags()).withRel("my-tags")
        );
        return ResponseEntity.ok(model);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TagDto>>> getMyTags() {
        List<EntityModel<TagDto>> tags = tagService.getTagsByCurrentUser()
                .stream()
                .map(tag -> EntityModel.of(tag,
                        linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel()
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TagDto>> collection = CollectionModel.of(tags,
                linkTo(methodOn(TagController.class).getMyTags()).withSelfRel(),
                linkTo(methodOn(TagController.class).createTag(null)).withRel("create")
        );

        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<EntityModel<TagDto>> createTag(@Valid @RequestBody TagDto dto) {
        TagDto created = tagService.createTag(dto);
        EntityModel<TagDto> model = EntityModel.of(created,
                linkTo(methodOn(TagController.class).getTagById(created.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).getMyTags()).withRel("my-tags")
        );
        return ResponseEntity.status(201).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TagDto>> updateTag(@PathVariable Long id, @Valid @RequestBody TagDto dto) {
        TagDto updated = tagService.updateTag(id, dto);
        EntityModel<TagDto> model = EntityModel.of(updated,
                linkTo(methodOn(TagController.class).getTagById(updated.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).getMyTags()).withRel("my-tags")
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<Void>> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);

        EntityModel<Void> model = EntityModel.of(null,
                linkTo(methodOn(TagController.class).getMyTags()).withRel("my-tags")
        );

        return ResponseEntity.ok(model); // 200 OK au lieu de 204
    }
}