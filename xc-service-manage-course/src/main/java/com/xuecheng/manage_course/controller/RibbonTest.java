package com.xuecheng.manage_course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/course")
public class RibbonTest {
    @Autowired
    RestTemplate restTemplate;
   //确定服务名
    String serviceId = "XC-SERVICE-MANAGE-CMS";
    //客户端通过ribbon去eureka中的服务列表中去根据服务名获取服务列表
    @GetMapping("/ceshi")
    public  void aaa() {
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://" + serviceId + "/cms/page/get/5a795ac7dd573c04508f3a56", Map.class);
        Map body = forEntity.getBody();
        System.out.println(body);
    }
}
