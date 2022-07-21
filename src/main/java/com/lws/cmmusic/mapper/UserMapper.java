package com.lws.cmmusic.mapper;

import com.lws.cmmusic.dto.UserCreateRequest;
import com.lws.cmmusic.dto.UserDto;
import com.lws.cmmusic.dto.UserUpdateRequest;
import com.lws.cmmusic.entity.User;
import com.lws.cmmusic.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity to Dto
    UserDto toDto(User user);

    // Dto to Vo
    UserVo toVo(UserDto userDto);

    // Dto to Entity
    User createEntity(UserCreateRequest userCreateRequest);

    // Update Entity
    User updateEntity(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
