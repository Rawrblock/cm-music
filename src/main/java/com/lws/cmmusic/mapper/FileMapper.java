package com.lws.cmmusic.mapper;

import com.lws.cmmusic.dto.FileDto;
import com.lws.cmmusic.dto.FileUploadRequest;
import com.lws.cmmusic.entity.File;
import com.lws.cmmusic.vo.FileVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    File createEntity(FileUploadRequest fileUploadRequest);

    FileVo toVo(FileDto fileDto);

    FileDto toDto(File file);
}
