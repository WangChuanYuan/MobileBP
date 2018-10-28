package service.operation;

import dao.client.ClientDAO;
import dao.operation.OperationDAO;
import org.springframework.stereotype.Service;
import po.Client;
import po.Operation;
import util.FeeType;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service(value = "dataService")
public class DataServiceImpl implements DataService {

    @Resource
    private OperationDAO operationDAO;

    @Resource
    private ClientDAO clientDAO;

    @Resource
    private FeeCalculator feeCalculator;

    @Override
    public double useData(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, double useLen, FeeType type) {
        if(!clientDAO.exists(phoneNo))
            return -1;
        double consume = feeCalculator.calculateFee(phoneNo, useLen, startTime, type);
        //记录本次流量使用
        Operation operation = new Operation(phoneNo, startTime, endTime, useLen, consume, type);
        operationDAO.save(operation);
        //扣费
        Client client = clientDAO.findByPN(phoneNo);
        double remain = client.getRemain() - consume;
        client.setRemain(remain);
        clientDAO.update(client);
        return consume;
    }
}
