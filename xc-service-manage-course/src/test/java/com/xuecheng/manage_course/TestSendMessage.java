package com.xuecheng.manage_course;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestSendMessage {

    @Autowired
    RestTemplate restTemplate;


    @Test
    public void ss(){
        for (int i = 0; i < 4; i++) {

            ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://XC-SERVICE-MANAGE-CMS/cms/page/get/5a795ac7dd573c04508f3a56", Map.class);
            Map body = forEntity.getBody();
            System.out.println(body);
        }
    }
}
