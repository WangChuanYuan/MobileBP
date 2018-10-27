package service.operation;

import dao.client.ClientDAO;
import dao.operation.OperationDAO;
import org.springframework.stereotype.Service;
import po.Client;
import po.Operation;
import po.Plan;
import service.order.OrderService;
import util.FeeType;
import vo.PackDetail;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static util.BaseFee.FEE;

@Service(value = "callService")
public class CallServiceImpl implements CallService {

    @Resource
    private OrderService orderService;

    @Resource
    private OperationDAO operationDAO;

    @Resource
    private ClientDAO clientDAO;

    @Override
    public double call(String phoneNo, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime monthStart = LocalDateTime.of(startTime.getYear(), startTime.getMonthValue(), 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(startTime.getYear(), startTime.getMonthValue(), startTime.toLocalDate().lengthOfMonth(), 23, 59);
        //用户当前套餐可支持的免费通话时间
        double freeLen = 0;
        List<PackDetail> packs = orderService.getOrderedPacks(phoneNo);
        for (int i = 0; i < packs.size(); i++) {
            List<Plan> plans = packs.get(i).getPack().getPlans();
            for (int j = 0; j < plans.size(); j++) {
                Plan plan = plans.get(j);
                if (plan.getType() == FeeType.CALL)
                    freeLen = freeLen + plan.getFreeLen();
            }
        }
        //本次通话时间
        double useLen = Duration.between(startTime, endTime).toMinutes();
        //用户本月已通话时间
        double useLenOfMonth = 0;
        List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
        for (int i = 0; i < operations.size(); i++) {
            Operation operation = operations.get(i);
            if (operation.getType() == FeeType.CALL)
                useLenOfMonth = useLenOfMonth + operation.getUseLen();
        }
        //计算本次通话费用
        double diff = useLenOfMonth - freeLen;
        double actualLen = useLen;
        double consume = 0;
        //如果本月通话时间未超过免费时间
        if (diff <= 0)
            actualLen = actualLen + diff;
        //如果加上本次通话超出免费时间
        if(actualLen > 0)
            consume = FEE[FeeType.CALL.ordinal()] * actualLen;
        //记录本次通话
        Operation operation = new Operation(phoneNo, startTime, endTime, useLen, consume, FeeType.CALL);
        operationDAO.save(operation);
        //扣费
        Client client = clientDAO.findByPN(phoneNo);
        double remain = client.getRemain() - consume;
        client.setRemain(remain);
        clientDAO.update(client);
        return consume;
    }
}
