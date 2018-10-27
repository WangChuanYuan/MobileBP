package service.order;

import dao.client.ClientDAO;
import dao.order.OrderDAO;
import dao.pack.PackDAO;
import org.springframework.stereotype.Service;
import po.Client;
import po.Order;
import po.Pack;
import util.OrderStatus;
import util.ResultMsg;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDAO orderDAO;

    @Resource
    private ClientDAO clientDAO;

    @Resource
    private PackDAO packDAO;

    @Override
    public ResultMsg orderPack(String phoneNo, long pid) {
        Client client = clientDAO.findByPN(phoneNo);
        Pack pack = packDAO.findByPid(pid);
        Order order = new Order(phoneNo, pid, LocalDateTime.now(), OrderStatus.ORDER);
        double remain = client.getRemain() - pack.getFee();
        client.setRemain(remain);
        int row = clientDAO.update(client);
        if (row > 0)
            row = orderDAO.save(order);
        return row > 0 ? ResultMsg.SUCCESS : ResultMsg.FAILURE;
    }

    @Override
    public ResultMsg cancelPack(String phoneNo, long pid, OrderStatus status) {
        return null;
    }

    @Override
    public List<Pack> getOrderedPacks(String phoneNo) {
        List<OrderStatus> statuses = new ArrayList<>();
        statuses.add(OrderStatus.ORDER);
        statuses.add(OrderStatus.PRECANCEL);
        List<Order> orders = orderDAO.findByPNAndStatusIn(phoneNo, statuses);
        List<Pack> packs = new ArrayList<>();
        orders.forEach(order -> packs.add(packDAO.findByPid(order.getPid())));
        return packs;
    }

    @Override
    public List<Pack> getPackHistory(String phoneNo) {
        List<Order> orders = orderDAO.findByPN(phoneNo);
        List<Pack> packs = new ArrayList<>();
        orders.forEach(order -> packs.add(packDAO.findByPid(order.getPid())));
        return packs;
    }
}
