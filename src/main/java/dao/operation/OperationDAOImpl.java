package dao.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import po.Operation;
import util.FeeType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository(value = "operationDAO")
public class OperationDAOImpl implements OperationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class OperationRowMapper implements RowMapper<Operation> {

        @Override
        public Operation mapRow(ResultSet resultSet, int i) throws SQLException {
            Operation operation = new Operation();
            operation.setOid(resultSet.getLong("oid"));
            operation.setPhoneNo(resultSet.getString("phoneNo"));
            operation.setStartTime(((Timestamp) resultSet.getObject("startTime")).toLocalDateTime());
            operation.setEndTime(((Timestamp) resultSet.getObject("endTime")).toLocalDateTime());
            operation.setUseLen(resultSet.getDouble("useLen"));
            operation.setFee(resultSet.getDouble("fee"));
            operation.setType(FeeType.valueOf(resultSet.getString("type")));
            return operation;
        }
    }

    @Override
    public int save(Operation operation) {
        String sql = "INSERT INTO Operation(phoneNo, startTime, endTime, useLen, fee, type) VALUE(?, ?, ?, ?, ?, ?)";
        int row = jdbcTemplate.update(sql, new Object[]{operation.getPhoneNo(), operation.getStartTime(), operation.getEndTime(), operation.getUseLen(), operation.getFee(), operation.getType().toString()});
        return row;
    }

    @Override
    public int update(Operation operation) {
        String sql = "UPDATE Operation SET phoneNo=?, startTime=?, endTime=?, useLen=?, fee=?, type=? WHERE oid=?";
        int row = jdbcTemplate.update(sql, new Object[]{operation.getPhoneNo(), operation.getStartTime(), operation.getEndTime(), operation.getUseLen(), operation.getFee(), operation.getType().toString(), operation.getOid()});
        return row;
    }

    @Override
    public List<Operation> findByPN(String phoneNo) {
        String sql = "SELECT * FROM Operation WHERE phoneNo=?";
        List<Operation> operations = jdbcTemplate.query(sql, new Object[]{phoneNo}, new OperationRowMapper());
        return operations;
    }

    @Override
    public List<Operation> findByPNAndTimeBetween(String phoneNo, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT * FROM Operation WHERE phoneNo=? AND startTime >= ? AND endTime <= ?";
        List<Operation> operations = jdbcTemplate.query(sql, new Object[]{phoneNo, startTime, endTime}, new OperationRowMapper());
        return operations;
    }

    @Override
    public List<Operation> findByPNAndTimeBetweenAndType(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, FeeType type) {
        String sql = "SELECT * FROM Operation WHERE phoneNo=? AND startTime >= ? AND endTime <= ? AND type=?";
        List<Operation> operations = jdbcTemplate.query(sql, new Object[]{phoneNo, startTime, endTime, type.toString()}, new OperationRowMapper());
        return operations;
    }

    @Override
    public double findSumOfUseLenByPNAndTimeBetweenAndType(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, FeeType type) {
        String sql = "SELECT sum(useLen) FROM Operation WHERE phoneNo=? AND startTime >= ? AND endTime <= ? AND type=?";
        double sum = jdbcTemplate.queryForObject(sql, new Object[]{phoneNo, startTime, endTime, type.toString()}, Double.class);
        return sum;
    }

    @Override
    public double findSumOfFeeByPNAndTimeBetweenAndType(String phoneNo, LocalDateTime startTime, LocalDateTime endTime, FeeType type) {
        String sql = "SELECT sum(fee) FROM Operation WHERE phoneNo=? AND startTime >= ? AND endTime <= ? AND type=?";
        double sum = jdbcTemplate.queryForObject(sql, new Object[]{phoneNo, startTime, endTime, type.toString()}, Double.class);
        return sum;
    }
}
