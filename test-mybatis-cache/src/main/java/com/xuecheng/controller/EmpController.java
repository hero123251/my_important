package com.xuecheng.controller;

import com.xuecheng.domain.Emp;
import com.xuecheng.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {
    @Autowired
    EmpService empService;

    @RequestMapping("/test/cache/{id}")
    public String test(@PathVariable("id") Integer id){
        String s = empService.testEmp(id);
        return s;

    }
}
