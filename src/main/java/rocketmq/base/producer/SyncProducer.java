package rocketmq.base.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 *
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/16
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/16              lishanglei      v1.0.0           Created
 */
@Slf4j
public class SyncProducer {

    public static void main(String[] args) throws Exception {

        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.5.62:9876;192.168.5.63:9876");
        //3. 启动producer
        producer.start();
        //4. 创建消息对象,指定主题Topic,tag和消息体
        for (int i = 0; i < 10; i++) {
            /**
             * args1:  消息主题Topic
             * args2:  消息Tag
             * args3:  消息内容
             */
            Message message = new Message("base", "Tag-1", ("Hello World" + i).getBytes());
            //5. 发送消息
            SendResult result = producer.send(message);
            SendStatus status = result.getSendStatus();
            String msgId = result.getMsgId();
            int queueId = result.getMessageQueue().getQueueId();
            log.info("发送结果[{}]", result);
            //模拟现实 ,线程休眠一秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6. 关闭生产者producer
        producer.shutdown();

    }
}
