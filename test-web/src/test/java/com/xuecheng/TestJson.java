package com.xuecheng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuecheng.domain.People;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestJson {

    //对象转json
    @Test
    public void ss(){
        String s = "sss";

        //自定义对象转json
        People p = new People();
        p.setUsername("admin");
        p.setPassword("123");

        //list集合转json
        //执行结果  ["a","b","c"]
        List l = new ArrayList();
        l.add("a");
        l.add("b");
        l.add("c");

        //map集合转json
        //执行结果  {"password":456,"username":123}
        Map m = new HashMap();
        m.put("username",123);
        m.put("password",456);
        String s2 = JSON.toJSONString(m);


        //数组转json
        //执行结果    [1,2,3,4,5]
        Integer [] i = new Integer[]{1,2,3,4,5};
        String s1 = JSON.toJSONString(i);
        System.out.println(s2);

    }

    //json转map
    @Test
    public void aa(){
        String json = "{\"password\":456,\"username\":123}";
    //将json转为map   执行结果  map{password=456, username=123}
                              //password456
                              //username123
        Map map = JSON.parseObject(json, Map.class);
        Object password = map.get("password");
        Object username = map.get("username");
        System.out.println("map"+map);
        System.out.println("password"+password);
        System.out.println("username"+username);
    }

    @Test
    public void testJSONToArray(){
       String json= "[1,2,3,4,5]";
        JSONArray objects = JSON.parseArray(json);
        for (Object object : objects) {
            System.out.println(object);
        }
        //测试结果：
        //1
        //2
        //3
        //4
        //5

    }

}
