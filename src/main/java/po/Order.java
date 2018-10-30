package po;

import util.OrderStatus;

import java.time.LocalDateTime;


public class Order {

    private String phoneNo;

    private long pid;

    private LocalDateTime time;

    private OrderStatus status;

    public Order() {
    }

    public Order(String phoneNo, long pid, LocalDateTime time, OrderStatus status) {
        this.phoneNo = phoneNo;
        this.pid = pid;
        this.time = time;
        this.status = status;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
