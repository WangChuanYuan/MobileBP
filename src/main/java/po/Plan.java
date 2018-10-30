package po;

import util.FeeType;


public class Plan {

    private double freeLen;

    private FeeType type;

    public Plan() {
    }

    public Plan(double freeLen, FeeType type) {
        this.freeLen = freeLen;
        this.type = type;
    }

    public double getFreeLen() {
        return freeLen;
    }

    public void setFreeLen(double freeLen) {
        this.freeLen = freeLen;
    }

    public FeeType getType() {
        return type;
    }

    public void setType(FeeType type) {
        this.type = type;
    }

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
