package vo;

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

    public Bill() {
    }

    public Bill(double packFee, double lenOfCall, double lenOfMsg, double lenOfLocalData, double lenOfGenData, double feeOfCall, double feeOfMsg, double feeOfLocalData, double feeOfGenData, int month) {
        this.packFee = packFee;
        this.lenOfCall = lenOfCall;
        this.lenOfMsg = lenOfMsg;
        this.lenOfLocalData = lenOfLocalData;
        this.lenOfGenData = lenOfGenData;
        this.feeOfCall = feeOfCall;
        this.feeOfMsg = feeOfMsg;
        this.feeOfLocalData = feeOfLocalData;
        this.feeOfGenData = feeOfGenData;
        this.month = month;
    }

    public double getPackFee() {
        return packFee;
    }

    public void setPackFee(double packFee) {
        this.packFee = packFee;
    }

    public double getLenOfCall() {
        return lenOfCall;
    }

    public void setLenOfCall(double lenOfCall) {
        this.lenOfCall = lenOfCall;
    }

    public double getLenOfMsg() {
        return lenOfMsg;
    }

    public void setLenOfMsg(double lenOfMsg) {
        this.lenOfMsg = lenOfMsg;
    }

    public double getLenOfLocalData() {
        return lenOfLocalData;
    }

    public void setLenOfLocalData(double lenOfLocalData) {
        this.lenOfLocalData = lenOfLocalData;
    }

    public double getLenOfGenData() {
        return lenOfGenData;
    }

    public void setLenOfGenData(double lenOfGenData) {
        this.lenOfGenData = lenOfGenData;
    }

    public double getFeeOfCall() {
        return feeOfCall;
    }

    public void setFeeOfCall(double feeOfCall) {
        this.feeOfCall = feeOfCall;
    }

    public double getFeeOfMsg() {
        return feeOfMsg;
    }

    public void setFeeOfMsg(double feeOfMsg) {
        this.feeOfMsg = feeOfMsg;
    }

    public double getFeeOfLocalData() {
        return feeOfLocalData;
    }

    public void setFeeOfLocalData(double feeOfLocalData) {
        this.feeOfLocalData = feeOfLocalData;
    }

    public double getFeeOfGenData() {
        return feeOfGenData;
    }

    public void setFeeOfGenData(double feeOfGenData) {
        this.feeOfGenData = feeOfGenData;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

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
