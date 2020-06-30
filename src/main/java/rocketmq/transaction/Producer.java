package rocketmq.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

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
public class Producer {

    public static void main(String[] args) throws Exception {

        //1. 创建消息生产者producer,并指定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("group5");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.5.62:9876;192.168.5.63:9876");
        //添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 在该方法中执行本地的事务
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {

                log.info("开始执行本地事务:"+message);
                if ("TAGA".equals(message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if ("TAGB".equals(message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else {
                    return LocalTransactionState.UNKNOW;
                }
            }

            /**
             * 该方法是mq进行消息事务状态的回查
             * @param messageExt
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

                log.info("当前消息的tag:"+messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        //3. 启动producer
        producer.start();

        String[] tag = {"TAGA", "TAGB", "TAGC"};
        //4. 创建消息对象,指定主题Topic,tag和消息体
        for (int i = 0; i < 3; i++) {
            /**
             * args1:  消息主题Topic
             * args2:  消息Tag
             * args3:  消息内容
             */
            Message message = new Message("TransactionTopic", tag[i],("Hello World" + i).getBytes());
            //5. 发送消息
            //args2: 针对该producer所有的消息还是某单一消息执行事务操作
            SendResult result = producer.sendMessageInTransaction(message, null);
            log.info("发送结果[{}]", result);
            //模拟现实 ,线程休眠一秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6. 关闭生产者producer
        //producer.shutdown();

    }
}
