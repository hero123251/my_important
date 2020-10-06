package com.xuecheng.manage_course;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFeign {

    @Autowired
    CmsPageClient cmsPageClient;
    @Test
    public void ssss(){
        for (int i = 0; i < 4; i++) {
            CmsPage byid = cmsPageClient.findByid("5a795ac7dd573c04508f3a56");
            System.out.println(byid);

        }
    }

}
