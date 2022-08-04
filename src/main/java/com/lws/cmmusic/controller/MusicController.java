package com.lws.cmmusic.controller;

import com.lws.cmmusic.dto.MusicCreateRequest;
import com.lws.cmmusic.dto.MusicUpdateRequest;
import com.lws.cmmusic.mapper.MusicMapper;
import com.lws.cmmusic.service.MusicService;
import com.lws.cmmusic.vo.MusicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/musics")
public class MusicController {

    private MusicService musicService;

    private MusicMapper musicMapper;

    // TODO: 2022/8/4 增
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MusicVo create(@Validated @RequestBody MusicCreateRequest musicCreateRequest) {
        System.out.println(1);
        return musicMapper.toVo(musicService.create(musicCreateRequest));
    }

    // TODO: 2022/8/4 改
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MusicVo update(@PathVariable String id, @Validated @RequestBody MusicUpdateRequest musicUpdateRequest) {
        return musicMapper.toVo(musicService.update(id, musicUpdateRequest));
    }

    // 修改商品状态:上架
    @PostMapping("/{id}/publish")
    public void publish(@PathVariable String id) {
        musicService.publish(id);
    }

    // 下架
    @PostMapping("/{id}/close")
    public void close(@PathVariable String id) {
        musicService.close(id);
    }

    // TODO: 2022/8/4 查
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MusicVo> list() {
        return musicService.list().stream().map(musicMapper::toVo).collect(Collectors.toList());
    }


    @Autowired
    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }

    @Autowired
    public void setMusicMapper(MusicMapper musicMapper) {
        this.musicMapper = musicMapper;
    }
}
