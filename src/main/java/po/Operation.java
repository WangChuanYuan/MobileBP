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

    private FeeType type;
}