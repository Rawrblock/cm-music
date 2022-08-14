package com.lws.cmmusic.mapper;

import com.lws.cmmusic.dto.SiteSettingDto;
import com.lws.cmmusic.vo.SiteSettingVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteSettingMapper {

    SiteSettingVo toVo(SiteSettingDto siteSettingDto);

}
