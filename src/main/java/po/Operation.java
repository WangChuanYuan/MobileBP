package po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FeeType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    private long oid;

    private String phoneNo;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double useLen;

    private double fee;

    private FeeType type;

    public Operation(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, double useLen, double fee, FeeType type) {
        this.phoneNo = phoneNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.useLen = useLen;
        this.fee = fee;
        this.type = type;
    }

    public void describe() {
        System.out.print(startTime + "至" + endTime + "：");
        switch (type) {
            case CALL:
                System.out.print("通话" + useLen + "分钟" + " ");
                break;
            case MESSAGE:
                System.out.print("发送短信" + useLen + "条" + " ");
                break;
            case LOCAL_DATA:
                System.out.print("使用本地流量" + useLen + "M" + " ");
                break;
            case GEN_DATA:
                System.out.print("使用全国流量" + useLen + "M" + " ");
                break;
        }
        System.out.println("消费" + fee + "元");
    }
}
