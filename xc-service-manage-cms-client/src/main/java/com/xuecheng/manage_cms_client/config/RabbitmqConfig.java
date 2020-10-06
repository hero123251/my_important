package com.xuecheng.manage_cms_client.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    //队列Bean的名称 queue_cms_postpage
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机 ex_routing_cms_postpage
    public static final String EX_ROUTING_CMS_POSTPAGE="ex_routing_cms_postage";

    //队列的名称
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;

    //交换机的名称
    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;
    /**
     *
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     * @return the exchange     
     */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE() {
        Queue queue = new Queue(queue_cms_postpage_name);
        return queue;
    }

    //绑定交换机和队列
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_CMS_POSTPAGE) Queue queue, @Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
