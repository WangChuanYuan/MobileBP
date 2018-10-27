package service.order;

import dao.client.ClientDAO;
import dao.operation.OperationDAO;
import dao.order.OrderDAO;
import dao.pack.PackDAO;
import org.springframework.stereotype.Service;
import po.*;
import util.FeeType;
import util.OrderStatus;
import util.ResultMsg;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static util.BaseFee.FEE;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDAO orderDAO;

    @Resource
    private ClientDAO clientDAO;

    @Resource
    private PackDAO packDAO;

    @Resource
    private OperationDAO operationDAO;

    @Override
    public ResultMsg orderPack(String phoneNo, long pid) {
        //已享受该套餐，不可订购
        if(orderDAO.exists(phoneNo, pid))
            return ResultMsg.FAILURE;
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
        //更改订单状态
        Order order = orderDAO.findByPNAndPid(phoneNo, pid);
        order.setStatus(status);
        int row = orderDAO.update(order);
        if (row > 0) {
            //如果是立即退款,则重新计算本月消费并额外扣费
            if (status == OrderStatus.CANCEL){
                LocalDateTime today = LocalDateTime.now();
                LocalDateTime monthStart = LocalDateTime.of(today.getYear(), today.getMonthValue(), 1, 0, 0);
                LocalDateTime monthEnd = LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 23, 59);
                List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
                //统计新的套餐优惠长度
                List<Pack> packs = getOrderedPacks(phoneNo); //此时已经取消了一个套餐
                double callFree = 0;
                double msgFree = 0;
                double localDataFree = 0;
                double genDataFree = 0;
                for(int i = 0; i < packs.size(); i++){
                    List<Plan> plans = packs.get(i).getPlans();
                    for (int j = 0; j < plans.size(); j++){
                        Plan plan = plans.get(j);
                        double freeLen = plan.getFreeLen();
                        switch (plan.getType()){
                            case CALL:
                                callFree = callFree + freeLen;
                                break;
                            case MESSAGE:
                                msgFree = msgFree + freeLen;
                                break;
                            case LOCAL_DATA:
                                localDataFree = localDataFree + freeLen;
                                break;
                            case GEN_DATA:
                                genDataFree = genDataFree + freeLen;
                                break;
                        }
                    }
                }
                //本月已扣费用
                Map<FeeType, DoubleSummaryStatistics> consumption = operations.parallelStream().
                        collect(Collectors.groupingBy(Operation::getType, Collectors.summarizingDouble(Operation::getFee)));
                //本月已用长度
                Map<FeeType, DoubleSummaryStatistics> useLen = operations.parallelStream().
                        collect(Collectors.groupingBy(Operation::getType, Collectors.summarizingDouble(Operation::getUseLen)));
                //取消套餐后额外计费
                double cost = 0;
                if(useLen.get(FeeType.CALL).getSum() > callFree)
                    cost += (useLen.get(FeeType.CALL).getSum() - callFree) * FEE[FeeType.CALL.ordinal()] - consumption.get(FeeType.CALL).getSum();
                if(useLen.get(FeeType.MESSAGE).getSum() > msgFree)
                    cost += (useLen.get(FeeType.MESSAGE).getSum() - msgFree) * FEE[FeeType.MESSAGE.ordinal()] - consumption.get(FeeType.MESSAGE).getSum();
                if(useLen.get(FeeType.LOCAL_DATA).getSum() > localDataFree)
                    cost += (useLen.get(FeeType.LOCAL_DATA).getSum() - localDataFree) * FEE[FeeType.LOCAL_DATA.ordinal()] - consumption.get(FeeType.LOCAL_DATA).getSum();
                if(useLen.get(FeeType.GEN_DATA).getSum() > genDataFree)
                    cost += (useLen.get(FeeType.GEN_DATA).getSum() - genDataFree) * FEE[FeeType.GEN_DATA.ordinal()] - consumption.get(FeeType.GEN_DATA).getSum();
                cost -= packDAO.findByPid(pid).getFee(); // 退还套餐费
                Client client = clientDAO.findByPN(phoneNo);
                double remain = client.getRemain() - cost;
                client.setRemain(remain);
                return clientDAO.update(client) > 0 ? ResultMsg.SUCCESS : ResultMsg.FAILURE;
            }
            else return ResultMsg.SUCCESS;
        }
        else return ResultMsg.FAILURE;
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
