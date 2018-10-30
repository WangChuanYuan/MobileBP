package vo;

import po.Pack;
import util.OrderStatus;

import java.time.LocalDateTime;


public class PackDetail {

    private Pack pack;

    private LocalDateTime time;

    private OrderStatus status;

    public PackDetail() {
    }

    public PackDetail(Pack pack, LocalDateTime time, OrderStatus status) {
        this.pack = pack;
        this.time = time;
        this.status = status;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
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

    public void describe() {
        System.out.println("订购时间：" + time);
        System.out.println("当前套餐状态：" + status);
        pack.describe();
    }
}
