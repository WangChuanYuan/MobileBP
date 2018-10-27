package service.client;

import po.Client;
import util.ResultMsg;

public interface ClientService {

    ResultMsg addClient(Client client);

    Client getClientByPN(String phoneNo);
}
