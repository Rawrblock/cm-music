package com.lws.cmmusic.vo;

import com.lws.cmmusic.enums.Storage;
import lombok.Data;

@Data
public class SiteSettingVo {

    private String bucket;

    private String region;

    private Storage storage;
    
}
