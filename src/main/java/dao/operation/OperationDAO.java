package dao.operation;

import po.Operation;
import util.FeeType;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationDAO {

    int save(Operation operation);

    int update(Operation operation);

    List<Operation> findByPN(String phoneNo);

    List<Operation> findByPNAndTimeBetween(String phoneNo, LocalDateTime startTime, LocalDateTime endTime);

    List<Operation> findByPNAndTimeBetweenAndType(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, FeeType type);

    double findSumOfUseLenByPNAndTimeBetweenAndType(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, FeeType type);

    double findSumOfFeeByPNAndTimeBetweenAndType(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, FeeType type);
}
