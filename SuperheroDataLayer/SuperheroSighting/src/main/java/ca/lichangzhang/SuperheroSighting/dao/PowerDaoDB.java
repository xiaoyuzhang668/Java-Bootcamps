package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Repository
public class PowerDaoDB implements PowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerById(int powerId) {
        try {
            final String sql = "SELECT * FROM power WHERE powerId = ?";
            return jdbc.queryForObject(sql, new PowerMapper(), powerId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        final String sql = "SELECT * FROM power";
        return jdbc.query(sql, new PowerMapper());
    }

    @Override
    @Transactional
    public Power addPower(Power power) {
        final String sql = "INSERT INTO power(name, description) "
                + "VALUES(?,?)";
        jdbc.update(sql,
                power.getName(),
                power.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);
        return power;
    }

    @Override
    public void updatePower(Power power) {
        final String sql = "UPDATE power SET name = ?, description = ? WHERE powerId = ?";
        jdbc.update(sql,
                power.getName(),
                power.getDescription(),
                power.getPowerId());
    }

    @Override
    @Transactional
    public void deletePowerById(int powerId) {
        final String sql = "UPDATE hero SET PowerId = NULL "
                + "WHERE powerId = ?";
        jdbc.update(sql, powerId);

        final String sql2 = "DELETE FROM power WHERE powerId = ?";
        jdbc.update(sql2, powerId);
    }

    @Override
    @Transactional
    public void deletePowerForTesting(int powerId) {

        final String sql = "DELETE S.* FROM sighting AS S "
                + "INNER JOIN hero AS H ON H.heroId =  S.heroId WHERE H.powerId = ?";
        jdbc.update(sql, powerId);

        final String sql2 = "DELETE FROM hero WHERE powerId = ?";
        jdbc.update(sql2, powerId);

        final String sql3 = "DELETE FROM power WHERE powerId = ?";
        jdbc.update(sql3, powerId);
    }

    public static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setPowerId(rs.getInt("powerId"));
            power.setName(rs.getString("name"));
            power.setDescription(rs.getString("description"));

            return power;
        }
    }
}
