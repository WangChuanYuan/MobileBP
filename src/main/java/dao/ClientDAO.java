package dao;

import po.Client;

public interface ClientDAO {

    int save(Client client);

    int update(Client client);

    Client findByPN(String phoneNo);
}
