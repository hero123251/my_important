package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.filesystem.service.FileSystemServiceTest;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    FileSystemServiceTest fileSystemServiceTest;

    @Override
    @PostMapping("/upload")
    public UploadFileResult upload(MultipartFile file, String filetag,String businesskey,String metadata) throws Exception {
//        UploadFileResult upload = fileSystemService.upload(file, filetag, businesskey, metadata);
        UploadFileResult upload = fileSystemServiceTest.upload(file, filetag, businesskey, metadata);

        return upload;
    }
}