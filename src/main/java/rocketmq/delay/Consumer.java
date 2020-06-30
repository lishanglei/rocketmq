package rocketmq.delay;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/17
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/17              lishanglei      v1.0.0           Created
 */
@Slf4j
public class Consumer {

    public static void main(String[] args) throws MQClientException {

        //1. 创建消费者Consumer,指定消费者组名
        // push:broker推送消息到consumer
        // pull:consumer主动从broker拉取消息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2. 指定NameServer地址
        consumer.setNamesrvAddr("192.168.5.62:9876;192.168.5.63:9876");
        //3. 订阅主题Topic和Tag
        consumer.subscribe("DelayTopic", "*");
        //4.设置消费消息模式,默认 负载均衡

        //广播模式
        //consumer.setMessageModel(MessageModel.BROADCASTING);
        //负载均衡模式
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        //5. 设置回调函数,处理消息
        consumer.registerMessageListener(
                new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                        for(MessageExt ext :list){
                            log.info("消息ID[{}],延迟时间[{}]",ext.getMsgId(),System.currentTimeMillis()-ext.getStoreTimestamp());
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }
        );
        //6. 启动消费者consumer
        consumer.start();
        System.out.println("消费者启动");
    }
}
