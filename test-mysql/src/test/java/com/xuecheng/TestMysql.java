package com.xuecheng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestMysql {


    @Test
    public void insertMysql() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loadtest?characterEncoding=utf-8&serverTimezone=UTC", "root", "mysql");
//        int t = 1685996;
        int t = 3372004;
        int count = 0;
        PreparedStatement stat = null;

//        for (int i = 1685997; i < 5000000; i++) {
        for (int i = 3372005; i < 5000000; i++) {
            t++;
            String sql = "insert into toot(age,name)values (?,?)";
            stat = con.prepareStatement(sql);
            stat.setInt(1, t);
            stat.setString(2, "李四");

            count = stat.executeUpdate();


        }


        System.out.println("新添加的记录：" + count);
        stat.close();
        con.close();
    }


}
