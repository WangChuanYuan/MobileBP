package dao;

import po.Order;
import util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        String sql = "insert into `Order`(phoneNo, pid, time, status) value(?, ?, ?, ?)";
        int row = jdbcTemplate.update(sql, new Object[]{order.getPhoneNo(), order.getPid(), order.getTime(), order.getStatus().toString()});
        return row;
    }

    @Override
    public int update(Order order) {
        String sql = "update `Order` set phoneNo=?, pid=?, time=?, status=? where oid=?";
        int row = jdbcTemplate.update(sql, new Object[]{order.getPhoneNo(), order.getPid(), order.getTime(), order.getStatus().toString(), order.getOid()});
        return row;
    }

    @Override
    public List<Order> findByPN(String phoneNo) {
        String sql = "select * from `Order` where phoneNo=?";
        List<Order> orders = jdbcTemplate.query(sql, new Object[]{phoneNo}, new OrderRowMapper());
        return orders;
    }

    @Override
    public List<Order> findByPNAndTimeBetween(String phoneNo, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "select * from `Order` where phoneNo=? and time between ? and ?";
        List<Order> orders = jdbcTemplate.query(sql, new Object[]{phoneNo, startTime, endTime}, new OrderRowMapper());
        return orders;
    }
}
