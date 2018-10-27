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
import java.sql.Timestamp;
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
            order.setPhoneNo(resultSet.getString("phoneNo"));
            order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            order.setPid(resultSet.getLong("pid"));
            order.setTime(((Timestamp)resultSet.getObject("time")).toLocalDateTime());
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
        String sql = "UPDATE `Order` SET time=?, status=? WHERE phoneNo=? and pid=?";
        int row = jdbcTemplate.update(sql, new Object[]{order.getTime(), order.getStatus().toString(), order.getPhoneNo(), order.getPid()});
        return row;
    }

    @Override
    public boolean exists(String phoneNo, long pid) {
        Order order = findByPNAndPid(phoneNo, pid);
        if(order == null)
            return false;
        else return order.getStatus() == OrderStatus.CANCEL ? false : true;
    }

    @Override
    public Order findByPNAndPid(String phoneNo, long pid) {
        String sql = "SELECT * FROM `Order` WHERE phoneNo=? AND pid=?";
        List<Order> orders = jdbcTemplate.query(sql, new Object[]{phoneNo, pid}, new OrderRowMapper());
        return orders.size() == 0 ? null : orders.get(0);
    }

    @Override
    public List<Order> findByPN(String phoneNo) {
        String sql = "SELECT * FROM `Order` WHERE phoneNo=?";
        List<Order> orders = jdbcTemplate.query(sql, new Object[]{phoneNo}, new OrderRowMapper());
        return orders;
    }

    @Override
    public List<Order> findByPNAndStatusIn(String phoneNo, List<OrderStatus> statuses) {
        String sql = "SELECT * FROM `Order` WHERE phoneNo=:phoneNo AND status IN (:statuses)";
        List<String> statusStr = new ArrayList<>();
        statuses.forEach(orderStatus -> statusStr.add(orderStatus.toString()));
        Map<String, Object> params = new HashMap<>();
        params.put("phoneNo", phoneNo);
        params.put("statuses", statusStr);
        List<Order> orders = parameterJdbcTemplate.query(sql, params, new OrderRowMapper());
        return orders;
    }

}
