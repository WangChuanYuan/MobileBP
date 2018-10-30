package po;

public class Client {

    private String phoneNo;

    private String name;

    private double remain;

    public Client() {
    }

    public Client(String phoneNo, String name, double remain) {
        this.phoneNo = phoneNo;
        this.name = name;
        this.remain = remain;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public void describe() {
        System.out.println("手机号：" + phoneNo);
        System.out.println("客户名：" + name);
        System.out.println("余额：" + remain);
    }
}
