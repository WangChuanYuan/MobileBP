package dao;

import po.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDAO {

    int save(Order order);

    int update(Order order);

    List<Order> findByPN(String phoneNo);

    List<Order> findByPNAndTimeBetween(String phoneNo, LocalDateTime startTime, LocalDateTime endTime);
}
