package po;

import util.FeeType;

import java.time.LocalDateTime;

public class Operation {

    private long oid;

    private String phoneNo;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double useLen;

    private double fee;

    private FeeType type;

    public Operation() {
    }

    public Operation(long oid, String phoneNo, LocalDateTime startTime, LocalDateTime endTime, double useLen, double fee, FeeType type) {
        this.oid = oid;
        this.phoneNo = phoneNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.useLen = useLen;
        this.fee = fee;
        this.type = type;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getUseLen() {
        return useLen;
    }

    public void setUseLen(double useLen) {
        this.useLen = useLen;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public FeeType getType() {
        return type;
    }

    public void setType(FeeType type) {
        this.type = type;
    }

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
