package com.xuecheng.manage_cms.dao;


import com.xuecheng.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 这是单元测试，在postman或者swagger测试叫接口测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {
    @Autowired
    PageService pageService;


    @Test
    public void freemarkerTest() throws Exception {
        String pageHtml = pageService.getPageHtml("5a795ac7dd573c04508f3a56");
        System.out.println(pageHtml);
    }

}
