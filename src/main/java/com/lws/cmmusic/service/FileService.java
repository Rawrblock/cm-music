package com.lws.cmmusic.service;

import com.lws.cmmusic.dto.FileDto;
import com.lws.cmmusic.dto.FileUploadDto;
import com.lws.cmmusic.dto.FileUploadRequest;
import com.lws.cmmusic.enums.Storage;

import java.io.IOException;

public interface FileService {

    FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException;

    FileDto finishUpload(String id);

    Storage getDefaultStorage();

}
