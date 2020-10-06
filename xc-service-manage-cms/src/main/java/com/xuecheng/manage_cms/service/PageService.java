package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    GridFSBucket gridFSBucket;
    //注入 GridFsTemplate 类
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 根据ID找页面
     *
     * @param pageId
     * @return
     */
    public CmsPage getPageById(String pageId) {
        //判断ID是否存在
        if (pageId == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXTISTS);
        }
        //判断页面是否存在
        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
        if (!(byId.isPresent())) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXTISTS);
        }
        CmsPage cmsPage = byId.get();
        return cmsPage;


    }

    //页面静态化
    public String getPageHtml(String pageId) throws Exception {
        CmsPage cmsPage = this.getPageById(pageId);


        //------获取数据模型
        //根据页面ID得到CmsPage
        //在CmsPage中得到DataUrl
        //然后根据DataUrl发送客户端请求，获得数据模型。
        Map modelByPageId = this.getModel(cmsPage);
        if(modelByPageId==null){
            //数据模型获取不到
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        //------获取模板
        //根据页面ID得到CmsPage
        //在CmsPage中得到templateId
        //然后根据templateId得到CmsTemplate
        //在CmsTemplate中，得到templateFileId
        //然后根据templateFileId，获取模板
        //执行页面静态化
        String templateByPageId = this.getTemplate(cmsPage);
            if(templateByPageId==null){
              ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            }
        //------执行页面静态化
        return this.generateHtml(templateByPageId,modelByPageId);
    }

    //获取数据模型
    //根据页面ID得到CmsPage
    //在CmsPage中得到DataUrl
    //然后根据DataUrl发送客户端请求，获得数据模型。
    private Map getModel(CmsPage cmsPage) {

        String dataUrl = cmsPage.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
            ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;

    }

    //获取模板
    //根据页面ID得到CmsPage
    //在CmsPage中得到templateId
    //然后根据templateId得到CmsTemplate
    //在CmsTemplate中，得到templateFileId
    //然后根据templateFileId，获取模板
    private String getTemplate(CmsPage cmsPage) throws IOException {

        String templateId = cmsPage.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> byId = cmsTemplateRepository.findById(templateId);
        if (byId.isPresent()) {
            CmsTemplate cmsTemplate = byId.get();
            //获取模板文件ID
            String templateFileId = cmsTemplate.getTemplateFileId();


            //然后根据templateFileId，获取模板

            //根据id查询文件
            //  GridFS在数据库中，默认使用fs.chunks和fs.files来存储文件。
            //      1.fs.files集合存放文件的信息；
            //      2.fs.chunks存放文件数据；
            GridFSFile gridFSFile =  gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream =  gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建gridFsResource，用于获取流对象
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            //获取流中的数据
            String s = IOUtils.toString(gridFsResource.getInputStream(),"UTF-8");

            return s;
        }
        return null;
    }

    //执行页面静态化（以模板字符串方式，实现网页静态化）
    private String generateHtml(String template,Map model) throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());

        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", template);
        configuration.setTemplateLoader(stringTemplateLoader);
        //得到模板
        Template template1 = configuration.getTemplate("template", "utf‐8");

        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
        //静态化内容
        System.out.println(content);
        return content;
    }


    //发布页面
    public ResponseResult postPage(String pageId) throws Exception {
        //执行页面静态化
        String content = this.getPageHtml(pageId);
        //保存文件内容到GridFS中
        CmsPage cmsPage = saveHtml(pageId, content);
        String siteId = cmsPage.getSiteId();

        //发送消息往rabbitmq
        String msg = sendPostPage(pageId);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,msg);
        //在channel.confirmSelect();
        //confirm模式具体的操作
        //        boolean b = rabbitTemplate.waitForConfirms(2);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //保存静态页面内容
    private CmsPage saveHtml(String pageId, String content){
        //查询页面
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXTISTS);
        }
        CmsPage cmsPage = optional.get();
        //存储之前先删除
        String htmlFileId = cmsPage.getHtmlFileId();
        if(StringUtils.isNotEmpty(htmlFileId)){
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }
        //保存html文件到GridFS
        InputStream inputStream = null;
        try {
            inputStream = IOUtils.toInputStream(content,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //用gridFsTemplate保存文件
        ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        //文件id
        String fileId = objectId.toString();
//        String string = objectId.toHexString();  这种和上一行有什么区别？
        //将文件id存储到cmspage中
        cmsPage.setHtmlFileId(fileId);
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }
    //发送消息往rabbitmq
    public String sendPostPage(String pageId){
//        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
//        if(!byId.isPresent()){
//            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXTISTS);
//        }
//        //获取站点ID
//        CmsPage cmsPage = byId.get();
//        String siteId = cmsPage.getSiteId();
        //将String转为Map，然后将map转为JSON字符串
        Map<String,String> map = new HashMap<>();
        map.put("pageId",pageId);
        String message = JSON.toJSONString(map);
        return message;

    }
}