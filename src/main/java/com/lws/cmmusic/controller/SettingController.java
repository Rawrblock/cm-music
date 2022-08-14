package com.lws.cmmusic.controller;

import com.lws.cmmusic.mapper.SiteSettingMapper;
import com.lws.cmmusic.service.SiteSettingService;
import com.lws.cmmusic.vo.SiteSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class SettingController {

    private SiteSettingService settingService;

    private SiteSettingMapper siteSettingMapper;

    @GetMapping("/site")
    public SiteSettingVo getSiteSetting() {
        return siteSettingMapper.toVo(settingService.getSiteSetting());
    }

    @Autowired
    public void setSettingService(SiteSettingService settingService) {
        this.settingService = settingService;
    }

    @Autowired
    public void setSiteSettingMapper(SiteSettingMapper siteSettingMapper) {
        this.siteSettingMapper = siteSettingMapper;
    }
}
