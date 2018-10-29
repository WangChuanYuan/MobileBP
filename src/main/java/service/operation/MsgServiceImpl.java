package service.operation;

import dao.client.ClientDAO;
import dao.operation.OperationDAO;
import org.springframework.stereotype.Service;
import po.Client;
import po.Operation;
import util.FeeType;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service(value = "msgService")
public class MsgServiceImpl implements MsgService {

    @Resource
    private OperationDAO operationDAO;

    @Resource
    private ClientDAO clientDAO;

    @Resource
    private FeeCalculator feeCalculator;

    @Override
    public double sendMsg(String phoneNo, LocalDateTime time) {
        if (!clientDAO.exists(phoneNo))
            return -1;
        double useLen = 1;
        double consume = feeCalculator.calculateFee(phoneNo, useLen, time, FeeType.MESSAGE);
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
