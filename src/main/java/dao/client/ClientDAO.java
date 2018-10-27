package dao.client;

import po.Client;

public interface ClientDAO {

    int save(Client client);

    int update(Client client);

    boolean exists(String phoneNo);

    Client findByPN(String phoneNo);
}
