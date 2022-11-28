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

    final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE locationId = ?";
    final String GET_ALL_LOCATIONS = "SELECT * FROM location ORDER BY locationId DESC";
    final String ADD_LOCATION = "INSERT INTO location(name, description,  address, phone, latitude, longitude) "
            + "VALUES(?, ?, ?, ?, ?, ?)";
    final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, "
            + "address = ?, phone = ?, latitude = ?, longitude = ? WHERE locationId = ?";
    final String DELETE_LOCATION_FOR_ID = "DELETE FROM location WHERE locationId = ?";
    final String GET_LOCATION_FOR_HERO = "SELECT DISTINCT L.* FROM location AS L "
            + "INNER JOIN sighting AS S ON L.locationId = S.locationId "
            + "WHERE S.heroId = ? ORDER BY L.name ";
    final String DELETE_SIGHTING = "DELETE FROM sighting ";
    final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization ";
    final String DELETE_HERO = "DELETE FROM hero ";
    final String DELETE_LOCATION = "DELETE FROM location ";
    final String DELETE_POWER = "DELETE FROM power ";

    @Override
    public Location getLocationById(int locationId) {
        try {
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), locationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {

        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {

        jdbc.update(ADD_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getPhone(),
                location.getLatitude(),
                location.getLongitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {

        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getPhone(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int locationId) {

        jdbc.update(DELETE_LOCATION_FOR_ID, locationId);
    }

//report all of the locations where a particular superhero has been seen.
    @Override
    public List<Location> getLocationForHero(Hero hero) {

        try {
            return jdbc.query(GET_LOCATION_FOR_HERO, new LocationMapper(), hero.getHeroId());
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void deleteLocationForTesting() {

        jdbc.update(DELETE_SIGHTING);
        jdbc.update(DELETE_HERO_ORGANIZATION);
        jdbc.update(DELETE_HERO);
        jdbc.update(DELETE_LOCATION);
        jdbc.update(DELETE_POWER);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setPhone(rs.getString("phone"));
            location.setLatitude(rs.getString("latitude"));
            location.setLongitude(rs.getString("longitude"));

            return location;
        }
    }

}
