package service.pack;

import dao.pack.PackDAO;
import org.springframework.stereotype.Service;
import po.Pack;
import util.ResultMsg;

import javax.annotation.Resource;

@Service(value = "packService")
public class PackServiceImpl implements PackService {

    @Resource
    private PackDAO packDAO;

    @Override
    public ResultMsg addPack(Pack pack) {
        return packDAO.save(pack) > 0 ? ResultMsg.SUCCESS : ResultMsg.FAILURE;
    }

    @Override
    public Pack getPackByPid(long pid) {
        return packDAO.findByPid(pid);
    }
}
