package com.lws.cmmusic.service.Impl;

import com.lws.cmmusic.dto.MusicCreateRequest;
import com.lws.cmmusic.dto.MusicDto;
import com.lws.cmmusic.dto.MusicUpdateRequest;
import com.lws.cmmusic.entity.Music;
import com.lws.cmmusic.enums.MusicStatus;
import com.lws.cmmusic.exception.BizException;
import com.lws.cmmusic.exception.ExceptionType;
import com.lws.cmmusic.mapper.MusicMapper;
import com.lws.cmmusic.repository.MusicRepository;
import com.lws.cmmusic.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicService {

    // 注入 实体转换
    private MusicMapper musicMapper;

    // 注入 JPA操作数据库
    private MusicRepository repository;

    // TODO: 2022/8/4 增
    // 新增歌曲
    @Override
    public MusicDto create(MusicCreateRequest musicCreateRequest) {
        Music music = musicMapper.createEntity(musicCreateRequest);
        music.setStatus(MusicStatus.DRAFT);
        Music demo = repository.save(music);
        return musicMapper.toDto(demo);
    }

    // TODO: 2022/8/4 改
    @Override
    public MusicDto update(String id, MusicUpdateRequest musicUpdateRequest) {
        Music existMusic = getMusic(id);
        Music music = musicMapper.updateEntity(existMusic, musicUpdateRequest);
        return musicMapper.toDto(repository.save(music));
    }


    @Override
    public void publish(String id) {
        Music music = getMusic(id);
        music.setStatus(MusicStatus.PUBLISHED);
        repository.save(music);
    }

    @Override
    public void close(String id) {
        Music music = getMusic(id);
        music.setStatus(MusicStatus.CLOSED);
        repository.save(music);
    }


    // TODO: 2022/8/4 查
    @Override
    public List<MusicDto> list() {
        return repository.findAll().stream().map(musicMapper::toDto).collect(Collectors.toList());
    }


    // 封装公共方法
    private Music getMusic(String id) {
        Optional<Music> musicOptional = repository.findById(id);
        if (!musicOptional.isPresent()) {
            throw new BizException(ExceptionType.MUSIC_NOT_FOUND);
        }
        return musicOptional.get();
    }

    @Autowired
    public void setMusicMapper(MusicMapper musicMapper) {
        this.musicMapper = musicMapper;
    }

    @Autowired
    public void setRepository(MusicRepository repository) {
        this.repository = repository;
    }
}
