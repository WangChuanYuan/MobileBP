package service.operation;

import dao.operation.OperationDAO;
import org.springframework.stereotype.Component;
import po.Operation;
import po.Plan;
import service.order.OrderService;
import util.FeeType;
import vo.PackDetail;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static util.BaseFee.FEE;

@Component
public class FeeCalculator {

    @Resource
    private OrderService orderService;

    @Resource
    private OperationDAO operationDAO;

    public double calculateFee(String phoneNo, double useLen, LocalDateTime time, FeeType type) {
        LocalDateTime monthStart = LocalDateTime.of(time.getYear(), time.getMonthValue(), 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.toLocalDate().lengthOfMonth(), 23, 59);
        //用户当前套餐可支持的免费长度
        double freeLen = 0;
        List<PackDetail> packs = orderService.getOrderedPacks(phoneNo);
        for (int i = 0; i < packs.size(); i++) {
            List<Plan> plans = packs.get(i).getPack().getPlans();
            for (int j = 0; j < plans.size(); j++) {
                Plan plan = plans.get(j);
                if (plan.getType() == type)
                    freeLen = freeLen + plan.getFreeLen();
            }
        }
        //用户本月已使用长度
        double useLenOfMonth = 0;
        List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
        for (int i = 0; i < operations.size(); i++) {
            Operation operation = operations.get(i);
            if (operation.getType() == type)
                useLenOfMonth = useLenOfMonth + operation.getUseLen();
        }
        //计算本次费用
        double diff = useLenOfMonth - freeLen;
        double actualLen = useLen;
        double consume = 0;
        //如果本月使用长度未超过免费长度
        if (diff <= 0)
            actualLen = actualLen + diff;
        //如果加上本次超出免费长度
        if (actualLen > 0)
            consume = FEE[FeeType.CALL.ordinal()] * actualLen;
        return consume;
    }
}
