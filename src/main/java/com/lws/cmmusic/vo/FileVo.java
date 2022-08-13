package com.lws.cmmusic.vo;

import com.lws.cmmusic.enums.FileStatus;
import com.lws.cmmusic.enums.FileType;
import lombok.Data;

@Data
public class FileVo extends BaseVo {

    private String name;

    private String key;

    private String ext;

    private Integer size;

    private FileType type;

    private FileStatus status;

}
