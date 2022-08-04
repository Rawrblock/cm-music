package com.lws.cmmusic.mapper;

import com.lws.cmmusic.dto.MusicCreateRequest;
import com.lws.cmmusic.dto.MusicDto;
import com.lws.cmmusic.dto.MusicUpdateRequest;
import com.lws.cmmusic.entity.Music;
import com.lws.cmmusic.vo.MusicVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MusicMapper {

    MusicDto toDto(Music music);

    MusicVo toVo(MusicDto musicDto);

    Music createEntity(MusicCreateRequest musicCreateRequest);

    Music updateEntity(@MappingTarget Music music, MusicUpdateRequest musicUpdateRequest);
}
