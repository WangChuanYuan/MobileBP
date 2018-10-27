package dao.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import po.Order;
import util.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value = "orderDAO")
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    private class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            Order order = new Order();
            order.setOid(resultSet.getLong("oid"));
            order.setPhoneNo(resultSet.getString("phoneNo"));
            order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            order.setPid(resultSet.getLong("pid"));
            order.setTime(LocalDateTime.ofInstant(resultSet.getTime("time").toInstant(), ZoneId.systemDefault()));
            return order;
        }
    }

    @Override
    public int save(Order order) {
        String sql = "INSERT INTO `Order`(phoneNo, pid, time, status) VALUE(?, ?, ?, ?)";
        int row = jdbcTemplate.update(sql, new Object[]{order.getPhoneNo(), order.getPid(), order.getTime(), order.getStatus().toString()});
        return row;
    }

    @Override
    public int update(Order order) {
        String sql = "UPDATE `Order` SET phoneNo=?, pid=?, time=?, status=? WHERE oid=?";
        int row = jdbcTemplate.update(sql, new Object[]{order.getPhoneNo(), order.getPid(), order.getTime(), order.getStatus().toString(), order.getOid()});
        return row;
    }

    @Override
    public List<Order> findByPN(String phoneNo) {
        String sql = "SELECT * FROM `Order` WHERE phoneNo=?";
        List<Order> orders = jdbcTemplate.query(sql, new Object[]{phoneNo}, new OrderRowMapper());
        return orders;
    }

    @Override
    public List<Order> findByPNAndStatusIn(String phoneNo, List<OrderStatus> statuses) {
        String sql = "SELECT * FROM `Order` WHERE phoneNo=:phoneNo AND status IN :statuses";
        List<String> statusStr = new ArrayList<>();
        statuses.forEach(orderStatus -> statusStr.add(orderStatus.toString()));
        Map<String, Object> params = new HashMap<>();
        params.put("phoneNo", phoneNo);
        params.put("statuses", statusStr);
        List<Order> orders = parameterJdbcTemplate.query(sql, params, new OrderRowMapper());
        return orders;
    }

}
