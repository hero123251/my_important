package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;

@Controller
public class CmsPagePreviewController extends BaseController {

    @Autowired
    PageService pageService;
    //接收到页面id
    @RequestMapping(value="/cms/preview/{pageId}",method = RequestMethod.GET)
     public void preview(@PathVariable("pageId")String pageId) throws Exception {
        String pageHtml = pageService.getPageHtml(pageId);

        //下面这是用到了ssi，所以返回头必须这样设置
        response.setHeader("content-type","text/html;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(pageHtml.getBytes("utf-8"));
    }
}