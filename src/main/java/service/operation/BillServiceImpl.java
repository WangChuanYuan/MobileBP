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
import java.util.List;

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
        List<Pack> packs = new ArrayList<>();
        orderService.getOrderedPacksBefore(phoneNo, monthEnd).forEach(packDetail -> packs.add(packDetail.getPack()));
        double packFee = packs.parallelStream().mapToDouble(Pack::getFee).sum();
        double callLen = operationDAO.findSumOfUseLenByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.CALL);
        double msgLen = operationDAO.findSumOfUseLenByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.MESSAGE);
        double localDataLen = operationDAO.findSumOfUseLenByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.LOCAL_DATA);
        double genDataLen = operationDAO.findSumOfUseLenByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.GEN_DATA);
        double callFee = operationDAO.findSumOfFeeByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.CALL);
        double msgFee = operationDAO.findSumOfFeeByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.MESSAGE);
        double localDataFee = operationDAO.findSumOfFeeByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.LOCAL_DATA);
        double genDataFee = operationDAO.findSumOfFeeByPNAndTimeBetweenAndType(phoneNo, monthStart, monthEnd, FeeType.GEN_DATA);
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
