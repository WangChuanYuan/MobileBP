package po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String phoneNo;

    private String name;

    private double remain;

    public void describe(){
        System.out.println("手机号：" + phoneNo);
        System.out.println("客户名：" + name);
        System.out.println("余额：" + remain);
    }
}
