package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeachplanMapper teachplanMapper;

    @Test
    public void testCourseBaseRepository() {
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if (optional.isPresent()) {
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper() {
        CourseBase courseBase = courseMapper.findCourseBaseById("297e7c7c62b888f00162b8a7dec20000");
        System.out.println(courseBase);
    }

    @Test
    public void testTeachplanMapper() {
        TeachplanNode teachplanNode = teachplanMapper.selectList("4028e581617f945f01617f9dabc40000");
        System.out.println(teachplanNode);
    }

    @Test
    public void testPage() {
        PageHelper.startPage(1,10);
        Page<CourseInfo> byPage = courseMapper.findByPage();
        List<CourseInfo> result = byPage.getResult();
        long total = byPage.getTotal();

        System.out.println("总记录"+total);
    }
    @Test
    public void testload() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
//        DriverManager.getConnection("jdbc：mysql：//127.0.0.1：3306 / imooc” ，“ root” ，“ root”");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/loadtest", "root", "mysql");
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usertest VALUES (?,?,?,NULL);");
//        INSERT INTO usertest VALUES ("小程程",1,"陕",NULL);
//        PreparedStatement preparedStatement = connection.prepareStatement("delete from usertest where id= ?");
        //DELETE FROM table_name
//WHERE some_column=some_value;
        for (int i = 8010; i < 18010; i++) {
            preparedStatement.setString(1,"小程程");
            preparedStatement.setString(3,"陕");
            preparedStatement.setInt(2,i);
            preparedStatement.addBatch();
        }

//        for (int i = 1; i < 1001; i++) {
//            preparedStatement.setInt(1,i);
//            preparedStatement.addBatch();
//        }


        int[] ints = preparedStatement.executeBatch();
        connection.commit();

    }
}