package po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.OrderStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private long oid;

    private String phoneNo;

    private long pid;

    private LocalDateTime time;

    private OrderStatus status;

    public Order(String phoneNo, long pid, LocalDateTime time, OrderStatus status) {
        this.phoneNo = phoneNo;
        this.pid = pid;
        this.time = time;
        this.status = status;
    }
}
