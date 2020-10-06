package com.xuecheng.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    //队列名称
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            //rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器
            factory.setVirtualHost("/");

            //创建与RabbitMQ服务的TCP连接
            connection = factory.newConnection();
            //创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();
            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * param1（queue）：队列名称
             * param2（durable）：是否持久化，如果持久化，mq重启后队列还在
             * param3（exclusive）：队列是否独占此连接，队列只允许在该连接中访问，如果connection连接关闭队列自动删除，如果此参数设置true可用于临时队列的创建
             * param4（autoDelete）：队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
             * param5（arguments）：队列参数，可以设置一个队列的扩展参数，比如：可设置存活时间
             */
            channel.queueDeclare(QUEUE, true, false, false, null);
            String message = "hello world 小程程" + System.currentTimeMillis();


            /**
             * 消息发布方法
             * param1(exchange)：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2(routingKey)：routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * param3(props)：消息包含的属性
             * param4(body): 消息体
             */

            /**
             * 这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，但是不能显示绑定或解除绑定
             * -----------默认的交换机，routingKey等于队列名称-----------
             * props: 是在header工作模式下用来操作 key---value 用的一个属性。
             */
            channel.basicPublish("", QUEUE, null, message.getBytes());//message是字符串，message.getBytes()该方法是将引用类型，转为字节。
            System.out.println("Send Message is :'" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


}