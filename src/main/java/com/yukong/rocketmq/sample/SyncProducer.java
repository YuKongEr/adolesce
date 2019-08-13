package com.yukong.rocketmq.sample;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author yukong
 * @date 2019-06-03 14:10
 * RocketMQ 同步生产者
 */
@Slf4j
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        // 生产者组名称
        DefaultMQProducer producer = new DefaultMQProducer("default_producer_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");

        // start()方法只需要启动的第一次调用即可
        producer.start();

        int recycle = 100;
        for (int i = 0; i < 100; i++) {
            // 创建消息实体对象  构造函数三个参数 topic 消息标题  tags 消息标记  body 消息主体
            Message message = new Message("topic", "tags",
                    ("hello rocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 发送消息到broker集群中的一个
            SendResult sendResult = producer.send(message);
            System.out.printf("send result = " +  sendResult);

        }
        // 关闭生产者
        producer.shutdown();
    }

}
