package com.lws.cmmusic.repository;

import com.lws.cmmusic.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
