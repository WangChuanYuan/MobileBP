package service.operation;

import dao.operation.OperationDAO;
import org.springframework.stereotype.Component;
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

    public double calculateFee(String phoneNo, double useLen, LocalDateTime endTime, FeeType type) {
        LocalDateTime monthStart = LocalDateTime.of(endTime.getYear(), endTime.getMonthValue(), 1, 0, 0);
        //用户当前套餐可支持的免费长度
        double freeLen = 0;
        List<PackDetail> packs = orderService.getOrderedPacksBefore(phoneNo, endTime);
        for (int i = 0; i < packs.size(); i++) {
            List<Plan> plans = packs.get(i).getPack().getPlans();
            for (int j = 0; j < plans.size(); j++) {
                Plan plan = plans.get(j);
                if (plan.getType() == type)
                    freeLen = freeLen + plan.getFreeLen();
            }
        }
        //用户从月初到目前为止已使用长度
        double useLenOfMonth = operationDAO.findSumOfUseLenByPNAndTimeBetweenAndType(phoneNo, monthStart, endTime, type);
        //用户从月初到目前为止总费用
        double feeOfMonth = operationDAO.findSumOfFeeByPNAndTimeBetweenAndType(phoneNo, monthStart, endTime, type);
        //计算加上本次操作的总费用
        double fee = 0;
        double actualLen = (useLen + useLenOfMonth) - freeLen;
        if (actualLen > 0)
            fee = actualLen * FEE[type.ordinal()];
        //计算本次操作费用
        double consume = (fee - feeOfMonth) > 0 ? (fee - feeOfMonth) : 0;
        return consume;
    }
}
