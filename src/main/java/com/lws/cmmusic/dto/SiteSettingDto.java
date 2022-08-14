package com.lws.cmmusic.dto;

import com.lws.cmmusic.enums.Storage;
import lombok.Data;

@Data
public class SiteSettingDto {

    private String bucket;

    private String region;

    private Storage storage;
    
}
