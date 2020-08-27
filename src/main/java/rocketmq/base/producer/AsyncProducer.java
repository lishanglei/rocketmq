package rocketmq.base.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 * 发送消息到broker后,线程不会堵塞等待mq的返回
 * 而是有回调函数等待mq异步回调
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/17
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/17              lishanglei      v1.0.0           Created
 */
@Slf4j
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {

        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer producer =new DefaultMQProducer("group1");

        //2.设置NameServer的地址
        producer.setNamesrvAddr("192.168.5.62:9876;192.168.5.63:9876");

        //3.启动producer
        producer.start();

        for(int i=0;i<10;i++){
            //4.创建消息
            Message message =new Message("base","Tag-2",("Hello World"+i).getBytes());

            //5.发送异步消息
            producer.send(message, new SendCallback() {
                //发送成功的回调函数
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功的结果[{}]"+sendResult);
                }
                //发送失败的回调函数
                @Override
                public void onException(Throwable throwable) {
                    log.error("发送异常[{}]",throwable);
                }
            });
            TimeUnit.SECONDS.sleep(1);

        }
        //6.关闭发送者
        producer.shutdown();
    }
}
