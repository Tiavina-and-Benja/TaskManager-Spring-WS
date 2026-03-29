package mg.itu.taskmanagerspringws.mapper;

import mg.itu.taskmanagerspringws.dto.RegisterRequestDto;
import mg.itu.taskmanagerspringws.dto.RegisterResponseDto;
import mg.itu.taskmanagerspringws.dto.UserDto;
import mg.itu.taskmanagerspringws.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterResponseDto userToRegisterResponseDto(User user);
    User registerRequestDtoToUser(RegisterRequestDto registerRequestDto);
    UserDto toDto(User user);
}
