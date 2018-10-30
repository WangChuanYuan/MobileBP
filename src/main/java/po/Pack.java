package po;

import java.util.List;


public class Pack {

    private long pid;

    private String name;

    private double fee;

    private List<Plan> plans;

    public Pack() {
    }

    public Pack(long pid, String name, double fee, List<Plan> plans) {
        this.pid = pid;
        this.name = name;
        this.fee = fee;
        this.plans = plans;
    }

    public Pack(String name, double fee, List<Plan> plans) {
        this.name = name;
        this.fee = fee;
        this.plans = plans;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
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
