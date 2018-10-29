package po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pack {

    private long pid;

    private String name;

    private double fee;

    private List<Plan> plans;

    public Pack(String name, double fee, List<Plan> plans) {
        this.name = name;
        this.fee = fee;
        this.plans = plans;
    }

    public void describe() {
        System.out.println("名称：" + name);
        System.out.println("月功能费：" + fee);
        System.out.println("包含优惠：");
        for (int i = 0; i < plans.size(); i++) {
            System.out.println(i + "：");
            plans.get(i).describe();
        }
    }
}
