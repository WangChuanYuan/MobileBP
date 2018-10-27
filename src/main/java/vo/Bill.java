package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private double packFee;

    private double feeOfCall;

    private double feeOfMsg;

    private double feeOfLocalData;

    private double feeOfGenData;

    private int month;
}
