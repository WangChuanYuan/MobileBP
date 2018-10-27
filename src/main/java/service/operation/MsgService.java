package service.operation;

import java.time.LocalDateTime;

public interface MsgService {

    double sendMsg(String phoneNo, LocalDateTime time);
}
