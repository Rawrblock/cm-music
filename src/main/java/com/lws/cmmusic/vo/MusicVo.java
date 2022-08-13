package com.lws.cmmusic.vo;

import com.lws.cmmusic.enums.MusicStatus;
import lombok.Data;

@Data
public class MusicVo extends BaseVo {

    private String name;

    private MusicStatus status;

    private String description;

}
