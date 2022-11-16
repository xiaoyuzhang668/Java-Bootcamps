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

    final String GET_POWER_BY_ID = "SELECT * FROM power WHERE powerId = ?";
    final String GET_ALL_POWERS = "SELECT * FROM power ORDER BY powerId DESC";
    final String ADD_POWER = "INSERT INTO power(name, description) "
            + "VALUES(?,?)";
    final String UPDATE_POWER = "UPDATE power SET name = ?, description = ? WHERE powerId = ?";
    final String DELETE_POWER_BY_ID = "UPDATE hero SET PowerId = NULL "
            + "WHERE powerId = ?";
    final String DELETE_POWER = "DELETE FROM power WHERE powerId = ?";

    @Override
    public Power getPowerById(int powerId) {

        try {
            return jdbc.queryForObject(GET_POWER_BY_ID, new PowerMapper(), powerId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {

        return jdbc.query(GET_ALL_POWERS, new PowerMapper());
    }

    @Override
    @Transactional
    public Power addPower(Power power) {

        jdbc.update(ADD_POWER,
                power.getName(),
                power.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);
        return power;
    }

    @Override
    public void updatePower(Power power) {

        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getDescription(),
                power.getPowerId());
    }

    @Override
    @Transactional
    public void deletePowerById(int powerId) {

        jdbc.update(DELETE_POWER_BY_ID, powerId);
        jdbc.update(DELETE_POWER, powerId);
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
