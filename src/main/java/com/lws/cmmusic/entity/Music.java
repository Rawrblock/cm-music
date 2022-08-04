package com.lws.cmmusic.entity;

import com.lws.cmmusic.enums.MusicStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
public class Music extends AbstractEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private MusicStatus status;

    private String description;

    // 知识：Music实体建立构造方法，并初始化。让实体管理的自己状态
//    public Music() {
//        this.setStatus(MusicStatus.DRAFT);
//    }
}
