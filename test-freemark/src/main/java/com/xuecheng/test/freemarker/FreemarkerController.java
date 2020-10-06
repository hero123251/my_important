package com.xuecheng.test.freemarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequestMapping("/freemarker")
@Controller
public class FreemarkerController {
    @Autowired
    RestTemplate restTemplate;



    @RequestMapping("/course")
    public String testcourse(Map<String, Object> map) {

        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31200/course/courseview/297e7c7c62b888f00162b8a7dec20000", Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);
        return "course";
    }
    @RequestMapping("/test")
    public String test(Map<String, Object> map) {
        map.put("name", "程大爷");
        return "test1";
    }

    @RequestMapping("/test1")
    public String test1(Map<String, Object> map) {

        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);

        return "index_banner";
    }


}