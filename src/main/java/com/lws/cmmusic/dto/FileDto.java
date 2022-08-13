package com.lws.cmmusic.dto;

import com.lws.cmmusic.enums.FileStatus;
import com.lws.cmmusic.enums.FileType;
import com.lws.cmmusic.enums.Storage;
import lombok.Data;

import java.util.Date;

@Data
public class FileDto {
    private String id;

    private String name;

    private String key;
    
    private String ext;

    private Integer size;

    private FileType type;

    private Storage storage;

    private FileStatus status;

    private Date createdTime;

    private Date updatedTime;
}
