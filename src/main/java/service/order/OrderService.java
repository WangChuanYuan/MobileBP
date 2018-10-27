package service.order;

import util.OrderStatus;
import util.ResultMsg;
import vo.PackDetail;

import java.util.List;

public interface OrderService {

    ResultMsg orderPack(String phoneNo, long pid);

    ResultMsg cancelPack(String phoneNo, long pid, OrderStatus status);

    List<PackDetail> getOrderedPacks(String phoneNo);

    List<PackDetail> getPackHistory(String phoneNo);
}
