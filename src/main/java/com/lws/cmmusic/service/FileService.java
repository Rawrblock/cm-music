package com.lws.cmmusic.service;

import com.lws.cmmusic.dto.FileDto;
import com.lws.cmmusic.dto.FileUploadDto;
import com.lws.cmmusic.dto.FileUploadRequest;

import java.io.IOException;

public interface FileService {

    FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException;

    FileDto finishUpload(String id);
}
