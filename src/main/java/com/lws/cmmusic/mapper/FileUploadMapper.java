package com.lws.cmmusic.mapper;

import com.lws.cmmusic.dto.FileUploadDto;
import com.lws.cmmusic.vo.FileUploadVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileUploadMapper {

    FileUploadVo toVo(FileUploadDto fileUploadDto);
}
