package com.lws.cmmusic.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

// 标注该 类变为一个共有的实体类
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    // 指定生成策略名
    @GeneratedValue(generator = "ksuid")
    // 指定生成策略代码文件
    @GenericGenerator(name = "ksuid", strategy = "com.lws.cmmusic.utils.KsuidIdentifierGenerator")
    private String id;

    //自动创建时间
    @CreationTimestamp
    private Date createdTime;

    // 自动创建更新时间
    @UpdateTimestamp
    private Date updatedTime;

}
