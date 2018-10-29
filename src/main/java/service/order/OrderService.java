package service.order;

import util.OrderStatus;
import util.ResultMsg;
import vo.PackDetail;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    ResultMsg orderPack(String phoneNo, long pid);

    ResultMsg cancelPack(String phoneNo, long pid, OrderStatus status);

    List<PackDetail> getOrderedPacksBefore(String phoneNo, LocalDateTime time);

    List<PackDetail> getPackHistory(String phoneNo);
}
