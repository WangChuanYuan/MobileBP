package dao.order;

import po.Order;
import util.OrderStatus;

import java.util.List;

public interface OrderDAO {

    int save(Order order);

    int update(Order order);

    List<Order> findByPN(String phoneNo);

    List<Order> findByPNAndStatusIn(String phoneNo, List<OrderStatus> statuses);
}
