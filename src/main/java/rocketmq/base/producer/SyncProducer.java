package rocketmq.base.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 * 发送消息至MQ后会堵塞,直到mq返回响应结果
 * 发送结果[SendResult [sendStatus=SEND_OK, msgId=C0A8050F74E873D16E93883B46CE0009, offsetMsgId=C0A8053E00002A9F00000000000059C3,
 * messageQueue=MessageQueue [topic=basex, brokerName=broker-a, queueId=1], queueOffset=8]]
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
        DefaultMQProducer producer = new DefaultMQProducer("group");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.5.62:9876;192.168.5.63:9876");
        producer.setSendMsgTimeout(10000);

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
