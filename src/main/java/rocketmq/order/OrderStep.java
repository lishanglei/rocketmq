package rocketmq.order;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
@Data
public class OrderStep {

    private Long orderId;
    private String desc;

    public static List<OrderStep> buildOrders(){

        List<OrderStep> orderStepList =new ArrayList<>();

        OrderStep orderStep =new OrderStep();
        //1039L : 创建 付款 推送 完成
        //1065L : 创建 付款
        //7235L : 创建 付款
        orderStep.setOrderId(1039L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep= new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep= new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep= new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);



        orderStep= new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep= new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep= new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        orderStep= new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("推送");
        orderStepList.add(orderStep);


        orderStep= new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        orderStep= new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        return orderStepList;

    }
}
