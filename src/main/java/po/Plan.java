package po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FeeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    private double freeLen;

    private FeeType type;

    public void describe() {
        System.out.println("优惠类型：" + type.toString());
        System.out.print("免费长度:" + freeLen);
        switch (type) {
            case CALL:
                System.out.println("分钟");
                break;
            case MESSAGE:
                System.out.println("条");
                break;
            case LOCAL_DATA:
                System.out.println("M");
                break;
            case GEN_DATA:
                System.out.println("M");
                break;
        }
    }
}
