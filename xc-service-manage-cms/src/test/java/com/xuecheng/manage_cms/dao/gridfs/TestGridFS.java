package com.xuecheng.manage_cms.dao.gridfs;


import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 测试用GridFS-----存储文件
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGridFS {
    //注入 GridFsTemplate 类
    @Autowired
    GridFsTemplate gridFsTemplate;

    /**
     * 用GridFS------存文件
     *
     * 存储原理说明：
     * 文件存储成功得到一个文件id 此文件id是fs.ﬁles集合中的主键。
     * 可以通过文件id查询fs.chunks表中的记录，得到文件的内容。
     */
    @Test
    public void testGridFs() throws FileNotFoundException {
        //要存储的文件
        File file = new File("F:\\xuecheng\\template\\course.ftl");
        //定义输入流
        FileInputStream inputStram = new FileInputStream(file);
        //向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(inputStram, "课程详情页小程程", "");
        //得到文件ID
        String fileId = objectId.toString();
        System.out.println(fileId);   //5efeec55e74bd2741c9a7206
    }


     @Autowired
     GridFSBucket gridFSBucket;

    /**
     * 用GridFS------取文件
     * @throws IOException
     */
    @Test
    public void queryFile() throws IOException {
        String fileId = "5ebe3bbce74bd24f8caa48a7";

        //根据id查询文件
        //  GridFS在数据库中，默认使用fs.chunks和fs.files来存储文件。
        //      1.fs.files集合存放文件的信息；
        //      2.fs.chunks存放文件数据；
        GridFSFile gridFSFile =  gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream =  gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        //获取流中的数据
        String s = IOUtils.toString(gridFsResource.getInputStream(),"UTF-8");
        System.out.println(s);
    }

    //删除文件
    @Test
    public void testDelFile() throws IOException {
        //根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5ebe111ae74bd23b90d631c0")));
    }
}