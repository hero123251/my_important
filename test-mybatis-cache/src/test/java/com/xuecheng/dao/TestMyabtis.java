package com.xuecheng.dao;

import com.xuecheng.dao.EmpMapper;
import com.xuecheng.domain.Emp;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestMyabtis {

    @Autowired
    EmpMapper empDao;

    @Autowired
    SqlSessionFactory sqlSessionFactory;





    @Test
    public void testYiJiCache2(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp1 = mapper.findEmpById(3);
        System.out.println(emp1);

        sqlSession.close();//

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        EmpMapper mapper2 = sqlSession2.getMapper(EmpMapper.class);
        mapper2.updateEmpById(3);
        sqlSession2.close();



//        sqlSession.clearCache(); //清除缓存也会导致不走缓存
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        EmpMapper mapper1 = sqlSession1.getMapper(EmpMapper.class);
        Emp emp2 = mapper1.findEmpById(3);
        System.out.println(emp2);
        sqlSession1.close();

        System.out.println(emp1==emp2);


    }
    @Test
    public void testYiJiCache1(){


        Emp empById2 = empDao.findEmpById(3);
        System.out.println(empById2);

//        empDao.updateEmpById(3);

        Emp empById3 = empDao.findEmpById(3);
        System.out.println(empById3);
        System.out.println(empById3 == empById2);


    }


}
