package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileSystemServiceTest {



    @Autowired
    FastFileStorageClient fastFileStorageClient;

    @Autowired
    FileSystemRepository fileSystemRepository;
    /**
     * 多文件上传
     *
     * @param
     * @return
     * @throws IOException
     */

    //上传文件

    public UploadFileResult upload(MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata) throws Exception {

        if(multipartFile ==null){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        String fileId = uploadFilesWithFastdfs(multipartFile);

        if(StringUtils.isEmpty(fileId)){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }

        //第二步：将文件id及其它文件信息存储到mongodb中。
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(filetag);
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        if(StringUtils.isNotEmpty(metadata)){
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileSystem save = fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,save);
    }



    public String uploadFilesWithFastdfs(MultipartFile multipartFile) throws Exception {
        List<FileInfo> fileInfoList = new ArrayList<>();


            String fileName = multipartFile.getOriginalFilename();
            StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), fileName.substring(fileName.lastIndexOf(".") + 1), null);

            //真实的情况会将数据信息进行保存，包含的信息（文件名，存储路径，是谁的附件等信息），那么入库的过程我就不说了
            return getfileUrl(storePath);

    }

    private String getfileUrl(StorePath storePath) {

        String path = storePath.getPath();
        System.out.println(path);
        return path;
    }
}
