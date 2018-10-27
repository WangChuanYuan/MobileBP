package service.operation;

import dao.operation.OperationDAO;
import org.springframework.stereotype.Service;
import po.Operation;
import po.Pack;
import service.order.OrderService;
import util.FeeType;
import vo.Bill;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "billService")
public class BillServiceImpl implements BillService {

    @Resource
    private OrderService orderService;

    @Resource
    private OperationDAO operationDAO;

    @Override
    public Bill getBillOf(String phoneNo) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime monthStart = LocalDateTime.of(today.getYear(), today.getMonthValue(), 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 23, 59);
        List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
        Map<FeeType, DoubleSummaryStatistics> consumption = operations.parallelStream().collect(Collectors.groupingBy(Operation::getType, Collectors.summarizingDouble(Operation::getFee)));
        List<Pack> packs = orderService.getOrderedPacks(phoneNo);
        double packFee = packs.parallelStream().mapToDouble(Pack::getFee).sum();
        Bill bill = new Bill(packFee, consumption.get(FeeType.CALL).getSum(), consumption.get(FeeType.MESSAGE).getSum(),
                consumption.get(FeeType.LOCAL_DATA).getSum(), consumption.get(FeeType.GEN_DATA).getSum(), today.getMonthValue());
        return bill;
    }

    @Override
    public List<Operation> getDetailsOf(String phoneNo, FeeType type) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime monthStart = LocalDateTime.of(today.getYear(), today.getMonthValue(), 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 23, 59);
        List<Operation> operations = operationDAO.findByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, type);
        return operations;
    }
}
