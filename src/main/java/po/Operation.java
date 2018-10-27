package po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FeeType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    private long oid;

    private String phoneNo;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double useLen;

    private double fee;

    private FeeType type;

    public Operation(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, double useLen, double fee, FeeType type) {
        this.phoneNo = phoneNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.useLen = useLen;
        this.fee = fee;
        this.type = type;
    }
}
