package com.lws.cmmusic.service;

import com.lws.cmmusic.dto.FileDto;
import com.lws.cmmusic.dto.FileUploadDto;
import com.lws.cmmusic.dto.FileUploadRequest;
import com.lws.cmmusic.enums.FileStatus;
import com.lws.cmmusic.enums.Storage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
class FileServiceTest {


    private FileService fileService;

    @Test
    void initUpload() throws IOException {
        FileUploadRequest fileUploadRequest = new FileUploadRequest();
        fileUploadRequest.setName("测试文件名");
        fileUploadRequest.setExt("mp3");
        fileUploadRequest.setKey("83574aba850778a5b06bfd57f55c98c");
        fileUploadRequest.setSize(30000);
        FileUploadDto fileUploadDto = fileService.initUpload(fileUploadRequest);
        Assertions.assertNotNull(fileUploadDto.getSecretKey());
        Assertions.assertNotNull(fileUploadDto.getSecretId());
        Assertions.assertNotNull(fileUploadDto.getSessionToken());
        Assertions.assertNotNull(fileUploadDto.getFileId());
        Assertions.assertEquals(fileUploadDto.getKey(), fileUploadRequest.getKey());

        log.info(fileUploadDto.toString());
    }

    @Test
    void finishUpload() throws IOException {
        FileUploadRequest fileUploadRequest = new FileUploadRequest();
        fileUploadRequest.setName("测试文件名");
        fileUploadRequest.setExt("mp3");
        fileUploadRequest.setKey("83574aba850778a5b06bfd57f55c98c");
        fileUploadRequest.setSize(30000);
        FileUploadDto fileUploadDto = fileService.initUpload(fileUploadRequest);

        FileDto finishedFile = fileService.finishUpload(fileUploadDto.getFileId());
        Assertions.assertEquals(fileUploadDto.getFileId(), finishedFile.getId());
        Assertions.assertEquals(FileStatus.UPLOADING, finishedFile.getStatus());
    }

    @Test
    void getDefaultStorage() {
        Storage storage = fileService.getDefaultStorage();
        Assertions.assertEquals(Storage.COS, storage);
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}