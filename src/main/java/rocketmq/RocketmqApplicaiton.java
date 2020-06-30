package rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/2
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/2              lishanglei      v1.0.0           Created
 */
@SpringBootApplication
public class RocketmqApplicaiton {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplicaiton.class,args);
        System.out.println("******************************mq服务启动*****************************");
    }

}



