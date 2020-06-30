package rocketmq.order;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

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
public class Producer {
    public static void main(String[] args) throws Exception {

        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.5.62:9876;192.168.5.63:9876");
        //3. 启动producer
        producer.start();
        //构建消息集合
        List<OrderStep> orderStepList = OrderStep.buildOrders();
        //4. 创建消息对象,指定主题Topic,tag和消息体
        for (int i = 0; i < orderStepList.size(); i++) {
            log.info("OrderStep[{}]",orderStepList.get(i));
            String body = orderStepList.get(i) + "";
            Message msg = new Message("OrderTopic", "Order", "i" + i, body.getBytes());
            /**
             * args1: 消息内容
             * args2: 消息队列的选择器
             * args3: 选择队列的业务标识(订单Id)
             */
            SendResult sendResult =producer.send(msg, new MessageQueueSelector() {
                /**
                 *
                 * @param list      队列集合
                 * @param message   消息对象
                 * @param o         参业务标识的数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Long orderId = (Long) o;
                    Long index = orderId % list.size();
                    MessageQueue messageQueue = list.get(Integer.valueOf(String.valueOf(index)));
                    log.info("队列大小[{}]该订单[{}],将被发送到队列[{}]",list.size(),o,messageQueue.getQueueId());
                    return messageQueue;
                }
            }, orderStepList.get(i).getOrderId());
            //System.out.println("发送结果: "+sendResult);
        }

        //6. 关闭生产者producer
        producer.shutdown();

    }
}
