package po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.FeeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    private double freeLen;

    private FeeType type;
}
