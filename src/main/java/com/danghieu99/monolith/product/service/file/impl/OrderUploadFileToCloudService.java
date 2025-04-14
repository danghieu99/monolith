package com.danghieu99.monolith.product.service.file.impl;

import com.danghieu99.monolith.product.service.file.OrderUploadFileService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class OrderUploadFileToCloudService implements OrderUploadFileService {

    @Override
    public void uploadImage(String fileName, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

    }
}
