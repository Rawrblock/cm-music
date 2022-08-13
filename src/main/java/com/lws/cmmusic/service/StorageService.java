package com.lws.cmmusic.service;

import com.lws.cmmusic.dto.FileUploadDto;

import java.io.IOException;

public interface StorageService {

    FileUploadDto initFileUpload() throws IOException;
}
