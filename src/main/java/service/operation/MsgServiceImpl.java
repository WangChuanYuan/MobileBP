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
        LocalDateTime monthEnd = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.toLocalDate().lengthOfMonth(), 23, 59);
        //用户当前套餐可支持的免费短信条数
        double freeLen = 0;
        List<PackDetail> packs = orderService.getOrderedPacks(phoneNo);
        for (int i = 0; i < packs.size(); i++) {
            List<Plan> plans = packs.get(i).getPack().getPlans();
            for (int j = 0; j < plans.size(); j++) {
                Plan plan = plans.get(j);
                if (plan.getType() == FeeType.MESSAGE)
                    freeLen = freeLen + plan.getFreeLen();
            }
        }
        double useLen = 1;
        //用户本月已发送短信条数
        double useLenOfMonth = 0;
        List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
        for (int i = 0; i < operations.size(); i++) {
            Operation operation = operations.get(i);
            if (operation.getType() == FeeType.MESSAGE)
                useLenOfMonth = useLenOfMonth + operation.getUseLen();
        }
        //计算条短信费用
        double diff = useLenOfMonth - freeLen;
        double actualLen = useLen;
        double consume = 0;
        //如果本月短信未超过免费条数
        if (diff <= 0)
            actualLen = actualLen + diff;
        //如果加上本条短信超出免费条数
        if(actualLen > 0)
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
