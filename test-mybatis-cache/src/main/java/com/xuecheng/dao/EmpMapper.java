package com.xuecheng.dao;

import com.xuecheng.domain.Emp;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface EmpMapper {

   public Emp findEmpById(int id);

   public void updateEmpById(int id);


}