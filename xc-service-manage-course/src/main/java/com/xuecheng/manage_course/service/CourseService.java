package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CoursePicRepository coursePicRepository;

    @Autowired
    CourseMarketRepository courseMarketRepository;

    public TeachplanNode findTeachplanList(String courseId) {
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;

    }

    /**
     * 添加课程计划
     */
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //判断前端传进来的是否非法
        String courseid = teachplan.getCourseid();
        String pname = teachplan.getPname();

        if (teachplan == null || StringUtils.isEmpty(courseid) || StringUtils.isEmpty(pname)) {
            //如果非法，则可以抛出“非法参数异常”
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }



        //获取parentId
        String parentid = teachplan.getParentid();

        if(StringUtils.isEmpty(parentid)){
            //获得根节点ID
            parentid = this.getTeachplanRoot(courseid);
        }

        Optional<Teachplan> byId = teachplanRepository.findById(parentid);
        Teachplan teachplan1 = byId.get();


        //新课程ID
        Teachplan teachplanNew = new Teachplan();
        BeanUtils.copyProperties(teachplan,teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        if(teachplan1.getGrade().equals("1")){
            teachplanNew.setGrade("2");
        }else {
            teachplanNew.setGrade("3");
        }

        teachplanRepository.save(teachplanNew);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询根节点，如果根节点没有，则设置。
    public String getTeachplanRoot(String courseid) {

        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseid);
        if (!courseBaseOptional.isPresent()) {
            return null;
        }
        //获取课程基本信息
        CourseBase courseBase = courseBaseOptional.get();


        //判断该课程计划是否有根节点
        //根据courseId和parentId查询
        List<Teachplan> teachplanList = teachplanRepository.findByAndCourseidAndParentid(courseid, "0");

        if (teachplanList == null || teachplanList.size() <= 0) {
            //查询不到，自动添加根节点
            Teachplan teachplanRoot = new Teachplan();
            teachplanRoot.setCourseid(courseid);
            teachplanRoot.setPname(courseBase.getName());
            teachplanRoot.setParentid("0");
            teachplanRoot.setGrade("1");//1级
            teachplanRoot.setStatus("0");//未发布
            teachplanRepository.save(teachplanRoot);
            //获得新的根节点的id
            String id = teachplanRoot.getId();
            return id;
        }
        String id1 = teachplanList.get(0).getId();
        return id1;
    }

    public List<CourseInfo> testservice(){
        PageHelper.startPage(1,10);
        Page<CourseInfo> byPage = courseMapper.findByPage();
        List<CourseInfo> result = byPage.getResult();
        return result;
    }

    /**
     * 查询课程详情页
     * @param id
     * @return
     */
    public CourseView getCourseView(String id){

        CourseView courseView = new CourseView();

        //课程基础信息
        Optional<CourseBase> courseBaseO = courseBaseRepository.findById(id);
        if(courseBaseO.isPresent()){
            CourseBase courseBase = courseBaseO.get();
            courseView.setCourseBase(courseBase);
        }

        //课程营销信息
        Optional<CourseMarket> courseMarketO = courseMarketRepository.findById(id);
        if(courseMarketO.isPresent()){
            CourseMarket courseMarket = courseMarketO.get();
            courseView.setCourseMarket(courseMarket);
        }

        //课程图片信息
        Optional<CoursePic> coursePicO = coursePicRepository.findById(id);
        if(coursePicO.isPresent()){
            CoursePic coursePic = coursePicO.get();
            courseView.setCoursePic(coursePic);
        }

        //课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);


        return courseView;
    }




    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;
    @Autowired
    CmsPageClient cmsPageClient;

    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> byId = courseBaseRepository.findById(courseId);
        if(byId.isPresent()){
            CourseBase courseBase = byId.get();
            return courseBase;
        }
        ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        return null;
    }
        //这个courseId是mysql中的coursebase中的id
    public CoursePublishResult preview(String courseId) {
        CourseBase courseBase = this.findCourseBaseById(courseId);
        //调用远程接口，保存课程详情页面
            //组装一个cmspage
            CmsPage cmsPage = new CmsPage();
            cmsPage.setSiteId(publish_siteId);//5efef9531822a64f3b02c19e
            cmsPage.setTemplateId(publish_templateId);//5a925be7b00ffc4b3c1578b5
            cmsPage.setDataUrl(publish_dataUrlPre+courseId);//http://localhost:31200/course/courseview/4028858162e0bc0a0162e0bfdf1a0000
            cmsPage.setPageName(courseId+".html");//4028858162e0bc0a0162e0bfdf1a0000  .html
            cmsPage.setPageAliase(courseBase.getName());//课程详情页面小程程
            cmsPage.setPagePhysicalPath(publish_page_physicalpath);//   /course/detail/
            cmsPage.setPageWebPath(publish_page_webpath);//   /course/detail/


        CmsPageResult cmsPageResult = cmsPageClient.save(cmsPage);
        CmsPage cmsPage1 = cmsPageResult.getCmsPage();
        String pageId = cmsPage1.getPageId();
        //拼接预览链接
        String url = previewUrl+pageId;
        return new CoursePublishResult(CommonCode.SUCCESS,url);
    }
}