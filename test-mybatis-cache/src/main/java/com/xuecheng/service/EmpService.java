package com.xuecheng.service;

import com.xuecheng.dao.EmpMapper;
import com.xuecheng.domain.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpService {
    @Autowired
    EmpMapper empMapper;

    public String testEmp(Integer id){
        Emp empById = empMapper.findEmpById(id);
        System.out.println(empById);

        Emp empById1 = empMapper.findEmpById(id);
        System.out.println(empById1);

        System.out.println(empById == empById1);
        return empById+"+"+empById1;
    }


}