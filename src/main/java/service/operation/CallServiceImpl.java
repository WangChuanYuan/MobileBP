package service.operation;

import dao.client.ClientDAO;
import dao.operation.OperationDAO;
import org.springframework.stereotype.Service;
import po.Client;
import po.Operation;
import util.FeeType;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

@Service(value = "callService")
public class CallServiceImpl implements CallService {

    @Resource
    private OperationDAO operationDAO;

    @Resource
    private ClientDAO clientDAO;

    @Resource
    private FeeCalculator feeCalculator;

    @Override
    public double call(String phoneNo, LocalDateTime startTime, LocalDateTime endTime) {
        //本次通话时间
        double useLen = Duration.between(startTime, endTime).toMinutes();
        double consume = feeCalculator.calculateFee(phoneNo, useLen, startTime, FeeType.CALL);
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
