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
        return clientDAO.save(client) > 0 ? ResultMsg.SUCCESS : ResultMsg.FAILURE;
    }
}
