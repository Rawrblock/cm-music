package com.lws.cmmusic.service.impl;

import com.lws.cmmusic.dto.FileUploadDto;
import com.lws.cmmusic.exception.BizException;
import com.lws.cmmusic.exception.ExceptionType;
import com.lws.cmmusic.service.StorageService;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

// COS上传文件逻辑
@Service("COS")
public class CosStorageServiceImpl implements StorageService {

    @Value("${cos.bucket}")
    private String bucket;

    @Value("${cos.secret_id}")
    private String secretId;

    @Value("${cos.secret_key}")
    private String secretKey;

    @Value("${cos.region}")
    private String region;

    @Override
    public FileUploadDto initFileUpload() {
        try {
            // 调用获取临时凭证接口
            Response response = CosStsClient.getCredential(getCosStsConfig());
            FileUploadDto fileUploadDto = new FileUploadDto();
            fileUploadDto.setBucket(bucket);
            fileUploadDto.setRegion(region);
            fileUploadDto.setSecretId(response.credentials.tmpSecretId);
            fileUploadDto.setSecretKey(response.credentials.tmpSecretKey);
            fileUploadDto.setSessionToken(response.credentials.sessionToken);
            return fileUploadDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ExceptionType.INNER_ERROR);
        }
    }

    // 获取COS上传临时凭证所需参数
    private TreeMap<String, Object> getCosStsConfig() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        config.put("secretId", secretId);
        config.put("secretKey", secretKey);

        // 临时密钥有效时长，单位是秒
        config.put("durationSeconds", 1800);
        // 配置全目录可以上传
        config.put("allowPrefixes", new String[]{
                "*"
        });

        config.put("bucket", bucket);
        config.put("region", region);
        String[] allowActions = new String[]{
                // 简单上传
                "name/cos:PutObject",
                "name/cos:PostObject",
                // 分片上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload"
        };
        config.put("allowActions", allowActions);
        return config;
    }
}
