package mg.itu.taskmanagerspringws.mapper;

import mg.itu.taskmanagerspringws.dto.TagDto;
import mg.itu.taskmanagerspringws.model.Tag;
import mg.itu.taskmanagerspringws.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(source = "user.id", target = "userId")
    TagDto toDto(Tag tag);

    @Mapping(source = "userId", target = "user.id")
    Tag toEntity(TagDto dto);

    // comme pour Project dans TaskMapper
    default User map(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
}