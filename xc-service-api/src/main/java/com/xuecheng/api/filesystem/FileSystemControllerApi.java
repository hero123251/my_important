package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
@Api(value="文件管理系统",description = "文件统一管理系统")
public interface FileSystemControllerApi {
    /**上传文件
     * @param multipartFile 文件
     * @param filetag 文件标签
     * @param businesskey 业务key
     * @param metedata 元信息
     *
     */
    @ApiOperation("管理课程图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="multipartFile",value = "文件",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="filetag",value = "文件标签",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="businesskey",value = "业务key",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="metadata",value = "元信息",required=true,paramType="path",dataType="int")
    })
   public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata) throws Exception;
}
