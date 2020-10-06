package com.xuecheng.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsTest {


    @Autowired
    RestClient restClient;

    @Autowired
    RestHighLevelClient client;


    /**
     * 创建索引库
     */
    @Test
    public void testCreateIndex() throws IOException {
        //创建索引请求对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("xc_course");
        //设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));

//        createIndexRequest.mapping(type,映射的json格式数据信息,XContentType.JSON);//设置索引库映射

        //创建索引操作客户端
        IndicesClient indices = client.indices();
        //创建响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
        //得到响应结果
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    /**
     * 删除索引库
     */
    @Test
    public void testDeleteIndex() throws IOException {
        //删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("xc_course");
        //操作索引的客户端
        IndicesClient indices = client.indices();
        //删除索引
        DeleteIndexResponse delete = indices.delete(deleteIndexRequest);
        //删除索引响应结果
        boolean acknowledged = delete.isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void testDelDoc() throws IOException {
        String id ="0D4oQ3MBihJI16LH6i-P";

        DeleteRequest request = new  DeleteRequest("xc_course","doc",id);
        DeleteResponse delete = client.delete(request);
        DocWriteResponse.Result result = delete.getResult();
        System.out.println(result);
    }
}
