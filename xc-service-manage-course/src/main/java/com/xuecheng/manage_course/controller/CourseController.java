package com.xuecheng.manage_course.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController extends HttpServlet implements CourseControllerApi {
    @Autowired
    CourseService courseService;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        TeachplanNode teachplanList = courseService.findTeachplanList(courseId);
        return teachplanList;
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {

        return courseService.addTeachplan(teachplan);
    }

    @Override
    @GetMapping("/find")
    public List<CourseInfo> findByPage() {

        List<CourseInfo> testservice = courseService.testservice();
        return testservice;
    }

    /**
     * 课程详情数据模型查询
     */
    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseService.getCourseView(id);
    }

    //课程预览
    @Override
    @GetMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id")String id) {
        return courseService.preview(id);

    }




}