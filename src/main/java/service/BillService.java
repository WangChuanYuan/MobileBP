package service;

import util.FeeType;
import vo.Bill;
import vo.Detail;

import java.util.List;

public interface BillService {

    Bill getBillOf(String phoneNo, int month);

    List<Detail> getDetailsOf(String phoneNo, FeeType type, int month);
}
