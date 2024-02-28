package org.epm.mapper;

import org.epm.User;
import org.epm.UserRequestDto;
import org.epm.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBirthday(dto.getBirthday());
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setBirthday(user.getBirthday());
        return dto;
    }

    public void updateEntityFromDto(UserRequestDto dto, User user) {
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBirthday(dto.getBirthday());
    }
}
