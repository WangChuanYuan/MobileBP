package dao.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import po.Pack;
import po.Plan;
import util.FeeType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository(value = "packDAO")
public class PackDAOImpl implements PackDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class PlanRowMapper implements RowMapper<Plan> {

        @Override
        public Plan mapRow(ResultSet resultSet, int i) throws SQLException {
            Plan plan = new Plan();
            plan.setFreeLen(resultSet.getDouble("freeLen"));
            plan.setType(FeeType.valueOf(resultSet.getString("type")));
            return plan;
        }
    }

    private class PackRowMapper implements RowMapper<Pack> {
        @Override
        public Pack mapRow(ResultSet resultSet, int i) throws SQLException {
            Pack pack = new Pack();
            pack.setPid(resultSet.getLong("pid"));
            pack.setName(resultSet.getString("name"));
            pack.setFee(resultSet.getDouble("fee"));
            return pack;
        }
    }

    private int deletePlans(long pid) {
        String sql = "DELETE FROM Plan WHERE pack_id=?";
        return jdbcTemplate.update(sql, pid);
    }

    private int[] savePlans(List<Plan> plans, long pid) {
        String sql = "INSERT INTO Plan(freeLan, type, pack_id) VALUES(?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Plan plan = plans.get(i);
                ps.setDouble(1, plan.getFreeLen());
                ps.setString(2, plan.getType().toString());
                ps.setLong(3, pid);
            }

            @Override
            public int getBatchSize() {
                return plans.size();
            }
        });
    }

    private List<Plan> findPlansByPid(long pid) {
        String sql = "SELECT freeLen, type FROM Plan WHERE pack_id=?";
        List<Plan> plans = jdbcTemplate.query(sql, new Object[]{pid}, new PlanRowMapper());
        return plans;
    }

    @Override
    public int save(Pack pack) {
        String packSQL = "INSERT INTO Pack(name, fee) VALUE(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(packSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pack.getName());
            ps.setDouble(2, pack.getFee());
            return ps;
        }, keyHolder);
        if (row <= 0)
            return -1;
        else {
            long pid = keyHolder.getKey().longValue();
            savePlans(pack.getPlans(), pid);
            return row;
        }
    }

    @Override
    public int update(Pack pack) {
        long pid = pack.getPid();
        String packSQL = "UPDATE Pack SET name=?, fee=? WHERE pid=?";
        int row = jdbcTemplate.update(packSQL, new Object[]{pack.getName(), pack.getFee(), pid});
        if (row <= 0)
            return -1;
        else {
            deletePlans(pid);
            savePlans(pack.getPlans(), pid);
            return row;
        }
    }

    @Override
    public Pack findByPid(long pid) {
        Pack pack = null;
        String sql = "SELECT * FROM Pack WHERE pid=?";
        List<Pack> packs = jdbcTemplate.query(sql, new Object[]{pid}, new PackRowMapper());
        if (packs.size() != 0) {
            pack = packs.get(0);
            pack.setPlans(findPlansByPid(pid));
        }
        return pack;
    }
}
