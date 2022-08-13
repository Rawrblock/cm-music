package com.lws.cmmusic.entity;

import com.lws.cmmusic.enums.FileStatus;
import com.lws.cmmusic.enums.FileType;
import com.lws.cmmusic.enums.Storage;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
public class File extends AbstractEntity {

    private String name;

    private String key;

    private String ext;

    private Integer size;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    private Storage storage;

    @Enumerated(EnumType.STRING)
    private FileStatus status = FileStatus.UPLOADING;
}
