package com.xuecheng.manage_cms_client.controller;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumerPostPage {

    @Autowired
    PageService pageService;
    @Autowired
    CmsPageRepository cmsPageRepository;


    //通过配置类，指定监听的队列
    //这里就不需要从config包下的配置类中，读取了，而是直接在yml文件中读取。
    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
//    @RabbitListener(queues = RabbitmqConfig.QUEUE_CMS_POSTPAGE)
    public void send_email(String msg,Message message, Channel channel) {
        //因为发送过来的是JSON数据字符串，所以需要解析
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String) map.get("pageId");
        //将页面保存到页面物理路径
        pageService.savePageToServerPath(pageId);
    }
}