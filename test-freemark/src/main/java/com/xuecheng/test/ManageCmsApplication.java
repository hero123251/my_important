package com.xuecheng.test;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.boot.autoconfigure.domain.EntityScan;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.ComponentScan;
        import org.springframework.data.mongodb.MongoDbFactory;
        import org.springframework.data.mongodb.gridfs.GridFsTemplate;
        import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
        import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan("com.xuecheng.test.freemarker.model")//扫描实体类
@ComponentScan(basePackages = "com.xuecheng.test.freemarker")
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}