package com.lws.cmmusic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lws.cmmusic.enums.MusicStatus;
import lombok.Data;

import java.util.Date;

@Data
public class MusicVo {

    private String id;

    private String name;

    private MusicStatus status;

    private String description;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
    private Date createdTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
    private Date updatedTime;

}
