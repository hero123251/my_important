package com.xuecheng.manage_cms_client.service;


import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    //此类型如果编译报错，请查看CSDN收藏夹的文章
    @Autowired
    GridFSBucket gridFSBucket;

    //将页面保存到页面物理路径
    public void savePageToServerPath(String pageId) {
        //根据pageId获取到CmsPage
        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
        if (!byId.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXTISTS);
        }
        CmsPage cmsPage = byId.get();
        //根据CmsPage中的htmlId来查询文件
        String htmlFileId = cmsPage.getHtmlFileId();

        //根据id查询文件
        //  GridFS在数据库中，默认使用fs.chunks和fs.files来存储文件。
        //      1.fs.files集合存放文件的信息；
        //      2.fs.chunks存放文件数据；
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        InputStream inputStream = null;
        try {
            inputStream = gridFsResource.getInputStream();
            if (inputStream == null) {
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取文件在本地的存储路径  站点物理路径 + 页面的物理路径 + 页面名称 = 门户主站页面的路径
        //根据站点ID获取站点对象
        String siteId = cmsPage.getSiteId();
        Optional<CmsSite> byId1 = cmsSiteRepository.findById(siteId);
        if (!byId1.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGESIDE_NULL);
        }
        CmsSite cmsSite = byId1.get();

        //获取站点物理路径
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();

        //获取页面的物理路径
        String pagePhysicalPath = cmsPage.getPagePhysicalPath();

        //获取页面名称
        String pageName = cmsPage.getPageName();

        String pagePath = pagePhysicalPath + sitePhysicalPath + pageName;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}