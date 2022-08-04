package com.lws.cmmusic.service;

import com.lws.cmmusic.dto.MusicCreateRequest;
import com.lws.cmmusic.dto.MusicDto;
import com.lws.cmmusic.dto.MusicUpdateRequest;

import java.util.List;

public interface MusicService {

    //----- 增 -----//
    // 新增歌曲
    MusicDto create(MusicCreateRequest musicCreateRequest);


    //----- 改 -----//
    // 通过id修改用户信息
    MusicDto update(String id, MusicUpdateRequest musicUpdateRequest);

    // 修改商品状态为上架
    void publish(String id);

    // 修改商品状态为下架
    void close(String id);


    //----- 查 -----//
    List<MusicDto> list();

}
