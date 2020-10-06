package com.xuecheng.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer04_subscribe_sms {
    //队列名称
    private static final String QUEUE_INFORM_SMS ="queue_inform_sms";
    //交换机名称
    private static final String EXCHANGE_FANOUT_INFORM ="exchange_fanout_inform";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器
        factory.setVirtualHost("/");

        //创建与RabbitMQ服务的TCP连接
        Connection connection = factory.newConnection();
        //创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
        Channel channel = connection.createChannel();

        /**
         * 声明队列，如果Rabbit中没有此队列将自动创建
         * param1（queue）：队列名称
         * param2（durable）：是否持久化，如果持久化，mq重启后队列还在
         * param3（exclusive）：队列是否独占此连接，队列只允许在该连接中访问，如果connection连接关闭队列自动删除，如果此参数设置true可用于临时队列的创建
         * param4（autoDelete）：队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
         * param5（arguments）：队列参数，可以设置一个队列的扩展参数，比如：可设置存活时间
         */
        channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM,BuiltinExchangeType.FANOUT);

        //绑定交换机和队列
        channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");
        


        DefaultConsumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                //路由Key
                String routingKey = envelope.getRoutingKey();
                //消息id
                Long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body,"utf-8");
                System.out.println("receive message..."+msg);
            }
        };




        /**
         * 监听队列 String queue , boolean autoAck,Consumer callback
         * 参数明细
         * 1、队列名称
         * 2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动回复
         * 3、消费消息的方法，消费者接收到消息后调用此方法。
         */
        channel.basicConsume(QUEUE_INFORM_SMS, true, consumer);
    }
}