package service.pack;

import po.Pack;
import util.ResultMsg;

public interface PackService {

    ResultMsg addPack(Pack pack);

    Pack getPackByPid(long pid);
}
