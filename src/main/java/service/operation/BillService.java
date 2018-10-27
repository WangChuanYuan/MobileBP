package service.operation;

import po.Operation;
import util.FeeType;
import vo.Bill;

import java.util.List;

public interface BillService {

    Bill getBillOf(String phoneNo, int year, int month);

    List<Operation> getDetailsOf(String phoneNo, int year, int month, FeeType type);
}
