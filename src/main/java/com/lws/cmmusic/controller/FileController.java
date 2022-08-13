package com.lws.cmmusic.controller;

import com.lws.cmmusic.dto.FileUploadRequest;
import com.lws.cmmusic.mapper.FileMapper;
import com.lws.cmmusic.mapper.FileUploadMapper;
import com.lws.cmmusic.service.FileService;
import com.lws.cmmusic.vo.FileUploadVo;
import com.lws.cmmusic.vo.FileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/files")
public class FileController {

    private FileService fileService;

    private FileUploadMapper fileUploadMapper;

    private FileMapper fileMapper;

    @PostMapping("/upload_init")
    public FileUploadVo initUpload(@Validated @RequestBody FileUploadRequest fileUploadRequest) throws IOException {
        return fileUploadMapper.toVo(fileService.initUpload(fileUploadRequest));
    }

    @PostMapping("/{id}/upload_finish")
    public FileVo finishUpload(@PathVariable String id) {
        return fileMapper.toVo(fileService.finishUpload(id));
    }


    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setFileUploadMapper(FileUploadMapper fileUploadMapper) {
        this.fileUploadMapper = fileUploadMapper;
    }

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
}
