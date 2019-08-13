package com.yukong.rocketmq.sample;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author yukong
 * @date 2019-06-03 14:24
 * RocketMQ 同步消费者
 */
public class SyncConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default_consumer_group_name");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 设置从何处开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 订阅消费的topic
        consumer.subscribe("topic", "");

        consumer.registerMessageListener((List<MessageExt> list, ConsumeConcurrentlyContext context) ->{
            System.out.println("当前线程名称: " + Thread.currentThread().getName() + ", 接受的消息为: " + list );
            // 返回消费成功标记
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();

    }

}
