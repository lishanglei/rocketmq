package rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/30
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/30              lishanglei      v1.0.0           Created
 */
@RocketMQMessageListener(
        topic = "springboot-rocketmq",
        consumerGroup = "${rocketmq.consumer.group}")
@Slf4j
@Component
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {

        log.info("接收到消息[{}]", s);
    }
}