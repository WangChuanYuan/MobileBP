package service.client;

import dao.client.ClientDAO;
import org.springframework.stereotype.Service;
import po.Client;
import util.ResultMsg;

import javax.annotation.Resource;

@Service(value = "clientService")
public class ClientServiceImpl implements ClientService {

    @Resource
    private ClientDAO clientDAO;

    @Override
    public ResultMsg addClient(Client client) {
        //该用户已经存在
        if (clientDAO.exists(client.getPhoneNo()))
            return ResultMsg.FAILURE;
        return clientDAO.save(client) > 0 ? ResultMsg.SUCCESS : ResultMsg.FAILURE;
    }
}
