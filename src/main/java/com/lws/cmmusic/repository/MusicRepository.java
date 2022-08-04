package com.lws.cmmusic.repository;

import com.lws.cmmusic.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, String> {
    // 通过歌曲ID获取歌曲信息
    Optional<Music> findById(String id);

}
