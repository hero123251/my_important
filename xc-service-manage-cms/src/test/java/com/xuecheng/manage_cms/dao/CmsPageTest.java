package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 这是单元测试，在postman或者swagger测试叫接口测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageTest {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    FileSystemRepository fileSystemRepository;
    //查询全部
    @Test
    public void main() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //分页查询
    @Test
    public void main2() {
        int page = 0;
        int size = 10;
        //Pageable注意是spring.domain 下的。它是一个接口，有好几个实现
        //这里用PageRequest这个实现，调用这个类的静态方法of来获得这个对象
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //分页带自定义条件查询
    @Test
    public void findByExample() {
        CmsPage cmsPage = new CmsPage();
        //根据站点ID查询
//        cmsPage.setPageId("5a754adf6abb500ad05688d911");
        //根据模板ID查询
//        cmsPage.setTemplateId("5a962b52b00ffc514038faf7");
        //模糊匹配的值
        cmsPage.setPageAliase("首");
        //模糊匹配器 下面这个方法是精确匹配
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        //包含
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //组装条件查询，参数一cmspage：条件查询，条件所在的类的类型
        //参数二：模糊匹配
        Example example = Example.of(cmsPage, exampleMatcher);


        int page = 0;
        int size = 10;
        //Pageable注意是spring.domain 下的。它是一个接口，有好几个实现
        //这里用PageRequest这个实现，调用这个类的静态方法of来获得这个对象
        Pageable pageable = PageRequest.of(page, size);
        Page all = cmsPageRepository.findAll(example, pageable);
        List content = all.getContent();
        System.out.println(content);
    }

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testRestTemplate() {
        //getForEntity 其中的get是指get请求,Map是指响应回来的类型。
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
//        ResponseEntity<Map> forEntity = restTemplate.getForEntity("https://fanyi.baidu.com/#en/zh/extract", Map.class);
        System.out.println(forEntity);
    }


    @Test
    public void testMong(){
        FileSystem fileSystem =new FileSystem();
        fileSystem.setFileName("dfdfd");
        fileSystem.setBusinesskey("keykeykye");
        fileSystem.setFilePath("pathpath");
        fileSystem.setFileWidth(20);
        FileSystem save = fileSystemRepository.save(fileSystem);
        System.out.println(save);
    }
}
