package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int locationId) {
        try {
            final String sql = "SELECT * FROM location WHERE locationId = ?";
            return jdbc.queryForObject(sql, new LocationMapper(), locationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String sql = "SELECT * FROM location";
        return jdbc.query(sql, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String sql = "INSERT INTO location(name, description, address, latitude, longitude) "
                + "VALUES(?, ?, ?, ?, ?)";
        jdbc.update(sql,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String sql = "UPDATE location SET name = ?, description = ?, "
                + "address = ?, latitude = ?, longitude = ? WHERE locationId = ?";
        jdbc.update(sql,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int locationId) {

        final String sql = "UPDATE sighting SET locationId = NULL "
                + "WHERE locationId = ?";
        jdbc.update(sql, locationId);

        final String sql2 = "DELETE FROM location WHERE locationId = ?";
        jdbc.update(sql2, locationId);
    }

//report all of the locations where a particular superhero has been seen.
    @Override
    public List<Location> getLocationForHero(Hero hero) {
        try {
            final String sql = "SELECT * FROM location AS L "
                    + "INNER JOIN sighting AS S ON L.locationId = S.locationId "
                    + "WHERE S.heroId = ?";
            return jdbc.query(sql, new LocationMapper(), hero.getHeroId());
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void deleteLocationForTesting() {
        final String sql = "DELETE FROM sighting ";
        jdbc.update(sql);

        final String sql2 = "DELETE FROM hero_organization ";
        jdbc.update(sql2);

        final String sql3 = "DELETE FROM hero ";
        jdbc.update(sql3);

        final String sql4 = "DELETE FROM location ";
        jdbc.update(sql4);

        final String sql5 = "DELETE FROM power ";
        jdbc.update(sql5);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getBigDecimal("latitude"));
            location.setLongitude(rs.getBigDecimal("longitude"));

            return location;
        }
    }

}
