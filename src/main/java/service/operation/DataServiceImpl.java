package service.operation;

import dao.client.ClientDAO;
import dao.operation.OperationDAO;
import po.Client;
import po.Operation;
import po.Pack;
import po.Plan;
import service.order.OrderService;
import util.FeeType;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static util.BaseFee.FEE;

public class DataServiceImpl implements DataService {

    @Resource
    private OrderService orderService;

    @Resource
    private OperationDAO operationDAO;

    @Resource
    private ClientDAO clientDAO;

    @Override
    public double useData(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, double useLen, FeeType type) {
        LocalDateTime monthStart = LocalDateTime.of(startTime.getYear(), startTime.getMonthValue(), 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(startTime.getYear(), startTime.getMonthValue(), startTime.getDayOfMonth(), 23, 59);
        //用户当前套餐可支持的免费流量长度
        double freeLen = 0;
        List<Pack> packs = orderService.getOrderedPacks(phoneNo);
        for (int i = 0; i < packs.size(); i++) {
            List<Plan> plans = packs.get(i).getPlans();
            for (int j = 0; j < plans.size(); j++) {
                Plan plan = plans.get(j);
                if (plan.getType() == type)
                    freeLen = freeLen + plan.getFreeLen();
            }
        }
        //用户本月已用流量
        double useLenOfMonth = useLen;
        List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
        for (int i = 0; i < operations.size(); i++) {
            Operation operation = operations.get(i);
            if (operation.getType() == type)
                useLenOfMonth = useLenOfMonth + operation.getUseLen();
        }
        //计算本次流量费用
        double actualLen = useLenOfMonth - freeLen;
        double consume = 0;
        if (actualLen > 0)
            consume = FEE[type.ordinal()] * actualLen;
        //记录本次流量使用
        Operation operation = new Operation(phoneNo, startTime, endTime, useLen, consume, type);
        operationDAO.save(operation);
        //扣费
        Client client = clientDAO.findByPN(phoneNo);
        double remain = client.getRemain() - consume;
        client.setRemain(remain);
        clientDAO.update(client);
        return consume;
    }
}
