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
import java.util.ArrayList;
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
    public Bill getBillOf(String phoneNo, int year, int month) {
        LocalDateTime monthStart = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(year, month, monthStart.toLocalDate().lengthOfMonth(), 23, 59);
        List<Operation> operations = operationDAO.findByPNAndTimeBetween(phoneNo, monthStart, monthEnd);
        Map<FeeType, DoubleSummaryStatistics> useLen = operations.parallelStream().collect(Collectors.groupingBy(Operation::getType, Collectors.summarizingDouble(Operation::getUseLen)));
        Map<FeeType, DoubleSummaryStatistics> consumption = operations.parallelStream().collect(Collectors.groupingBy(Operation::getType, Collectors.summarizingDouble(Operation::getFee)));
        List<Pack> packs = new ArrayList<>();
        orderService.getOrderedPacks(phoneNo).forEach(packDetail -> packs.add(packDetail.getPack()));
        double packFee = packs.parallelStream().mapToDouble(Pack::getFee).sum();
        double callLen = 0;
        double msgLen = 0;
        double localDataLen = 0;
        double genDataLen = 0;
        double callFee = 0;
        double msgFee = 0;
        double localDataFee = 0;
        double genDataFee = 0;
        if (consumption.get(FeeType.CALL) != null) {
            callFee = consumption.get(FeeType.CALL).getSum();
            callLen = useLen.get(FeeType.CALL).getSum();
        }
        if (consumption.get(FeeType.MESSAGE) != null) {
            msgFee = consumption.get(FeeType.MESSAGE).getSum();
            msgLen = useLen.get(FeeType.MESSAGE).getSum();
        }
        if (consumption.get(FeeType.LOCAL_DATA) != null) {
            localDataFee = consumption.get(FeeType.LOCAL_DATA).getSum();
            localDataLen = useLen.get(FeeType.LOCAL_DATA).getSum();
        }
        if (consumption.get(FeeType.GEN_DATA) != null) {
            genDataFee = consumption.get(FeeType.GEN_DATA).getSum();
            genDataLen = useLen.get(FeeType.GEN_DATA).getSum();
        }
        Bill bill = new Bill(packFee, callLen, msgLen, localDataLen, genDataLen, callFee, msgFee, localDataFee, genDataFee, month);
        return bill;
    }

    @Override
    public List<Operation> getDetailsOf(String phoneNo, int year, int month, FeeType type) {
        LocalDateTime monthStart = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(year, month, monthStart.toLocalDate().lengthOfMonth(), 23, 59);
        List<Operation> operations = operationDAO.findByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, type);
        return operations;
    }
}
