package service.operation;

import java.time.LocalDateTime;

public interface CallService {

    double call(String phoneNo, LocalDateTime startTime, LocalDateTime endTime);
}
