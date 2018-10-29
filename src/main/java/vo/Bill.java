package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private double packFee;

    private double lenOfCall;

    private double lenOfMsg;

    private double lenOfLocalData;

    private double lenOfGenData;

    private double feeOfCall;

    private double feeOfMsg;

    private double feeOfLocalData;

    private double feeOfGenData;

    private int month;

    public void describe() {
        System.out.println("月功能费：" + packFee + "元");
        System.out.println("通话：" + lenOfCall + "分钟");
        System.out.println("短信：" + lenOfMsg + "条");
        System.out.println("本地流量：" + lenOfLocalData + "M");
        System.out.println("全国流量：" + lenOfGenData + "M");
        System.out.println("通话费用：" + feeOfCall + "元");
        System.out.println("短信费用：" + feeOfMsg + "元");
        System.out.println("本地流量费用：" + feeOfLocalData + "元");
        System.out.println("全国流量费用：" + feeOfGenData + "元");
        System.out.println("月份：" + month + "月");
    }
}
