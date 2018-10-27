package dao.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import po.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository(value = "clientDAO")
public class ClientDAOImpl implements ClientDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ClientRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet resultSet, int i) throws SQLException {
            Client client = new Client();
            client.setPhoneNo(resultSet.getString("phoneNo"));
            client.setName(resultSet.getString("name"));
            return client;
        }
    }

    @Override
    public int save(Client client) {
        String sql = "INSERT INTO `Client`(phoneNo, name) VALUE(?, ?)";
        int row = jdbcTemplate.update(sql, new Object[]{client.getPhoneNo(), client.getName()});
        return row;
    }

    @Override
    public int update(Client client) {
        String sql = "UPDATE `Client` SET name=? WHERE phoneNo=?";
        int row = jdbcTemplate.update(sql, new Object[]{client.getName(), client.getPhoneNo()});
        return row;
    }

    @Override
    public boolean exists(String phoneNo) {
        return findByPN(phoneNo) != null ? true : false;
    }

    @Override
    public Client findByPN(String phoneNo) {
        String sql = "SELECT * FROM `Client` WHERE phoneNo=?";
        List<Client> clients = jdbcTemplate.query(sql, new Object[]{phoneNo}, new ClientRowMapper());
        return clients.size() == 0 ? null : clients.get(0);
    }

}
