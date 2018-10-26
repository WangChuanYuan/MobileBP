package service;

import po.Pack;
import util.OrderStatus;
import util.ResultMsg;

import java.util.List;

public interface OrderService {

    ResultMsg orderPack(String phoneNo, long pid);

    ResultMsg cancelPack(String phoneNo, long pid, OrderStatus status);

    List<Pack> getOrderedPacks(String phoneNo);

    List<Pack> getPackHistory(String phoneNo);
}
