package dao;

import entity.FeeType;
import entity.Operation;
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
public class OperationDAOImpl implements OperationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class OperationRowMapper implements RowMapper<Operation> {

        @Override
        public Operation mapRow(ResultSet resultSet, int i) throws SQLException {
            Operation operation = new Operation();
            operation.setOid(resultSet.getLong("oid"));
            operation.setPhoneNo(resultSet.getString("phoneNo"));
            operation.setStartTime(LocalDateTime.ofInstant(resultSet.getTime("startTime").toInstant(), ZoneId.systemDefault()));
            operation.setEndTime(LocalDateTime.ofInstant(resultSet.getTime("endTime").toInstant(), ZoneId.systemDefault()));
            operation.setType(FeeType.valueOf(resultSet.getString("type")));
            return operation;
        }
    }

    @Override
    public int save(Operation operation) {
        String sql = "insert into Operation(phoneNo, startTime, endTime, type) value(?, ?, ?, ?)";
        int row = jdbcTemplate.update(sql, new Object[]{operation.getPhoneNo(), operation.getStartTime(), operation.getEndTime(), operation.getType().toString()});
        return row;
    }

    @Override
    public int update(Operation operation) {
        String sql = "update Operation set phoneNo=?, startTime=?, endTime=?, type=? where oid=?";
        int row = jdbcTemplate.update(sql, new Object[]{operation.getPhoneNo(), operation.getStartTime(), operation.getEndTime(), operation.getType().toString(), operation.getOid()});
        return row;
    }

    @Override
    public List<Operation> findByPN(String phoneNo) {
        String sql = "select * from Operation where phoneNo=?";
        List<Operation> operations = jdbcTemplate.query(sql, new Object[]{phoneNo}, new OperationRowMapper());
        return operations;
    }

    @Override
    public List<Operation> findByType(FeeType type) {
        String sql = "select * from Operation where type=?";
        List<Operation> operations = jdbcTemplate.query(sql, new Object[]{type.toString()}, new OperationRowMapper());
        return operations;
    }

    @Override
    public List<Operation> findByPNAndTimeBetween(String phoneNo, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "select * from Operation where phoneNo=? and startTime <= ? and endTime >= ?";
        List<Operation> operations = jdbcTemplate.query(sql, new Object[]{phoneNo, startTime, endTime}, new OperationRowMapper());
        return operations;
    }
}
