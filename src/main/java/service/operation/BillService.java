package service.operation;

import po.Operation;
import util.FeeType;
import vo.Bill;

import java.util.List;

public interface BillService {

    Bill getBillOf(String phoneNo);

    List<Operation> getDetailsOf(String phoneNo, FeeType type);
}
