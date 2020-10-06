package com.xuecheng.search;

import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient restClient;

    /**
     * 搜索全部
     */
    @Test
    public void testSearchAll() throws IOException {
        //组装搜索条件
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");//指定类型   -----> FROM `course_base`
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //指定查询条件 --------------> where .....
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //指定查询返回的字段 ---------> SELECT id , NAME ,users
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        //执行搜索
        SearchResponse search = client.search(searchRequest);

        //解析搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String id = hit.getId();
            String index = hit.getIndex();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);


        }
    }

    //分页查询
    @Test
    public void testPage() throws IOException {
        //组装搜索条件
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //组装分页条件
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);
        //指定搜索条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.fetchSource(new String[]{"name", "description", "studymodel"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        //执行搜索
        SearchResponse search = client.search(searchRequest);

        //解析搜索结果
        SearchHits hits = search.getHits();
        SearchHit[] searchHits = hits.getHits();
        //总记录数
        long totalHits = hits.getTotalHits();
        for (SearchHit hit : searchHits) {
            String id = hit.getId();

            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            System.out.println(name);
            System.out.println(description);
            System.out.println(studymodel);


        }

    }

    //term query 和id 查询  注意 ： id查询时，是termsQuery 而不是termQuery
    @Test
    public void testTermQuer() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.termQuery("name","java"));
        String[] ids = new String[]{"1", "2"};
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id", ids));
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest);

        SearchHits hits = search.getHits();
        SearchHit[] searchHits = hits.getHits();

        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }


    //matchquery方案
    @Test
    public void testmatch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name", "description", "studymodel"}, new String[]{});
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "java开发").operator(Operator.OR));

        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest);

        SearchHits hits = search.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }


    }

    //测试multimatchquery，可以在多个字段中去匹配
    @Test
    public void testmultimatchquery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        HashMap  mapFiles = new HashMap<>();
        mapFiles.put("name",10);
        mapFiles.put("description",20);
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("java开发", "name", "description").fields(mapFiles));


        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest);

        SearchHits hits = search.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String sourceAsString = hit.getSourceAsString();

            System.out.println(sourceAsString);
        }

    }


    //测试booleanquery，可以在多个字段中去匹配
    @Test
    public void testbooleanquery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("java开发", "name", "description");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);


        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});


        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest);

        SearchHits hits = search.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String sourceAsString = hit.getSourceAsString();

            System.out.println(sourceAsString);
        }
    }


    //测试filter，可以在多个字段中去匹配
    @Test
    public void testfilter() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(matchAllQueryBuilder);

        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel",201001));


        searchSourceBuilder.query(boolQueryBuilder);

//        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel",201002));
//        TermQueryBuilder studymodel = QueryBuilders.termQuery("studymodel", 201002);


//        searchSourceBuilder.query(studymodel);
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});


        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest);

        SearchHits hits = search.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String sourceAsString = hit.getSourceAsString();

            System.out.println(sourceAsString);
        }
    }


    @Test
    public void sss() {
        double ceil = Math.ceil(2.4);
        System.out.println(ceil);
    }


}
