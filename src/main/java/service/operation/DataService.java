package service.operation;

import util.FeeType;

import java.time.LocalDateTime;

public interface DataService {

    double useData(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, double useLen, FeeType type);
}
