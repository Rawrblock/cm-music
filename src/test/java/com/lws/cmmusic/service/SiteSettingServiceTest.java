package com.lws.cmmusic.service;

import com.lws.cmmusic.dto.SiteSettingDto;
import com.lws.cmmusic.enums.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SiteSettingServiceTest {

    private SiteSettingService service;

    @Test
    void getSiteSetting() {
        SiteSettingDto siteSettingDto = service.getSiteSetting();
        Assertions.assertNotNull(siteSettingDto.getBucket());
        Assertions.assertNotNull(siteSettingDto.getRegion());
        Assertions.assertSame(Storage.class, siteSettingDto.getStorage().getClass());
    }

    @Autowired
    public void setService(SiteSettingService service) {
        this.service = service;
    }
}