package com.lws.cmmusic.service.impl;

import com.lws.cmmusic.dto.FileDto;
import com.lws.cmmusic.dto.FileUploadDto;
import com.lws.cmmusic.dto.FileUploadRequest;
import com.lws.cmmusic.entity.File;
import com.lws.cmmusic.enums.FileStatus;
import com.lws.cmmusic.enums.Storage;
import com.lws.cmmusic.exception.BizException;
import com.lws.cmmusic.exception.ExceptionType;
import com.lws.cmmusic.mapper.FileMapper;
import com.lws.cmmusic.repository.FileRepository;
import com.lws.cmmusic.service.FileService;
import com.lws.cmmusic.service.StorageService;
import com.lws.cmmusic.utils.FileTypeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private Map<String, StorageService> storageServices;

    private FileRepository fileRepository;

    private FileMapper fileMapper;

    // 获取文件上传信息
    @Override
    @Transactional
    public FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException {
        // 通过创建File实体
        File file = fileMapper.createEntity(fileUploadRequest);
        // 判断后缀
        file.setType(FileTypeTransformer.getFileTypeFromExt(fileUploadRequest.getExt()));
        file.setStorage(getDefaultStorage());
        File savedFile = fileRepository.save(file);

        // 通过接口获取STS令牌
        FileUploadDto fileUploadDto = storageServices.get(getDefaultStorage().name()).initFileUpload();

        fileUploadDto.setKey(savedFile.getKey());
        fileUploadDto.setFileId(savedFile.getId());
        return fileUploadDto;
    }


    @Override
    public FileDto finishUpload(String id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (!fileOptional.isPresent()) {
            throw new BizException(ExceptionType.FILE_NOT_FOUND);
        }

        // TODO: 2022/8/12 只有上传才能更新finish; 权限判断

        // TODO: 2022/8/12 验证远程文件是否存在

        File file = fileOptional.get();
        file.setStatus(FileStatus.UPLOADING);
        return fileMapper.toDto(fileRepository.save(file));
    }

    // TODO: 2022/8/10 后台设置当前Storage
    public Storage getDefaultStorage() {
        return Storage.COS;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Autowired
    public void setStorageServices(Map<String, StorageService> storageServices) {
        this.storageServices = storageServices;
    }
}
