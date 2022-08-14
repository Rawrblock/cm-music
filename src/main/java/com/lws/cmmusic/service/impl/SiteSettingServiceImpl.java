package com.lws.cmmusic.service.impl;

import com.lws.cmmusic.dto.SiteSettingDto;
import com.lws.cmmusic.service.FileService;
import com.lws.cmmusic.service.SiteSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SiteSettingServiceImpl implements SiteSettingService {

    @Value("${cos.bucket}")
    private String bucket;

    @Value("${cos.region}")
    private String region;

    private FileService fileService;

    @Override
    public SiteSettingDto getSiteSetting() {
        SiteSettingDto siteSettingDto = new SiteSettingDto();
        siteSettingDto.setBucket(bucket);
        siteSettingDto.setRegion(region);
        siteSettingDto.setStorage(fileService.getDefaultStorage());
        return siteSettingDto;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
