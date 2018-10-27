package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import po.Pack;
import util.OrderStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackDetail {

    private Pack pack;

    private LocalDateTime time;

    private OrderStatus status;

    public void describe(){
        System.out.println("订购时间：" + time);
        System.out.println("当前套餐状态：" + status);
        pack.describe();
    }
}
