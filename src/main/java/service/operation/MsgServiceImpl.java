package service.operation;

import dao.client.ClientDAO;
import dao.operation.OperationDAO;
import org.springframework.stereotype.Service;
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

@Service(value = "msgService")
public class MsgServiceImpl implements MsgService {

    @Resource
    private OrderService orderService;

    @Resource
    private OperationDAO operationDAO;

    @Resource
    private ClientDAO clientDAO;

    @Override
    public double sendMsg(String phoneNo, LocalDateTime time) {
        LocalDateTime monthStart = LocalDateTime.of(time.getYear(), time.getMonthValue(), 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), 23, 59);
        //用户当前套餐可支持的免费短信条数
        double freeLen = 0;
        List<Pack> packs = orderService.getOrderedPacks(phoneNo);
        for (int i = 0; i < packs.size(); i++) {
            List<Plan> plans = packs.get(i).getPlans();
            for (int j = 0; j < plans.size(); j++) {
                Plan plan = plans.get(j);
                if (plan.getType() == FeeType.MESSAGE)
                    freeLen = freeLen + plan.getFreeLen();
            }
        }
        double useLen = 1;
        //用户本月已发送短信条数
        double useLenOfMonth = useLen;
        List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
        for (int i = 0; i < operations.size(); i++) {
            Operation operation = operations.get(i);
            if (operation.getType() == FeeType.MESSAGE)
                useLenOfMonth = useLenOfMonth + operation.getUseLen();
        }
        //计算本条短信费用
        double actualLen = useLenOfMonth - freeLen;
        double consume = 0;
        if (actualLen > 0)
            consume = FEE[FeeType.MESSAGE.ordinal()] * actualLen;
        //记录本条短信
        Operation operation = new Operation(phoneNo, time, time, useLen, consume, FeeType.MESSAGE);
        operationDAO.save(operation);
        //扣费
        Client client = clientDAO.findByPN(phoneNo);
        double remain = client.getRemain() - consume;
        client.setRemain(remain);
        clientDAO.update(client);
        return consume;
    }
}
