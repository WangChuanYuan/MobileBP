package dao;

import entity.FeeType;
import entity.Operation;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationDAO {

    int save(Operation operation);

    int update(Operation operation);

    List<Operation> findByPN(String phoneNo);

    List<Operation> findByType(FeeType type);

    List<Operation> findByPNAndTimeBetween(String phoneNo, LocalDateTime startTime, LocalDateTime endTime);
}
