package rocketmq.batch;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发送批量消息
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
public class Producer {

    public static void main(String[] args) throws Exception {

        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.5.62:9876;192.168.5.63:9876");
        //3. 启动producer
        producer.start();
        //4. 创建消息对象,指定主题Topic,tag和消息体
        List<Message> messageList = new ArrayList<>();
        Message message1 = new Message("BatchTopic", "Tag-1", ("Hello World" + 1).getBytes());
        Message message2 = new Message("BatchTopic", "Tag-1", ("Hello World" + 2).getBytes());
        Message message3 = new Message("BatchTopic", "Tag-1", ("Hello World" + 3).getBytes());
        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);
        //5. 发送消息
        SendResult result = producer.send(messageList);
        log.info("发送结果[{}]", result);
        //模拟现实 ,线程休眠一秒
        TimeUnit.SECONDS.sleep(1);

        //6. 关闭生产者producer
        producer.shutdown();

    }
}
