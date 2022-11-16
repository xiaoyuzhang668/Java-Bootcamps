package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dao.HeroDaoDB.HeroMapper;
import ca.lichangzhang.SuperheroSighting.dao.LocationDaoDB.LocationMapper;
import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
    final String GET_HERO_FOR_SIGHTING = "SELECT H.* FROM hero AS H "
            + "INNER JOIN sighting AS S ON S.heroId = H.heroId WHERE S.sightingId = ?";
    final String GET_POWER_FOR_HERO = "SELECT P.* FROM power AS P "
            + "INNER JOIN hero AS H ON P.powerId = H.powerId WHERE H.heroId = ?";
    final String GET_ORGANIZATION_FOR_HERO = "SELECT O.* FROM organization AS O "
            + "INNER JOIN hero_organization AS HO ON O.organizationId = HO.organizationId WHERE HO.heroId = ?";
    final String GET_LOCATION_FOR_SIGHTING = "SELECT L.* FROM location AS L "
            + "INNER JOIN sighting AS S ON S.locationId = L.locationId WHERE S.sightingId = ?";
    final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting ORDER BY sightingId DESC";
    final String GET_ALL_10_RECENT_SIGHTING = "SELECT * FROM sighting ORDER BY sightingDate DESC "
            + "LIMIT 10 ";
    final String ADD_SIGHTING = "INSERT INTO sighting(heroId, locationId, description, sightingDate) "
            + "VALUES(?,?,?,?)";
    final String UPDATE_SIGHTING = "UPDATE sighting SET heroId = ?, locationId = ?, description = ?, "
            + "sightingDate = ? WHERE sightingId = ?";
    final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
    final String GET_SIGHTING_FOR_DATE = "SELECT * FROM sighting WHERE CAST(sightingDate AS DATE) = ?";
    final String GET_SIGHTING_FOR_HERO = "SELECT * FROM sighting WHERE heroId = ?";

    @Override
    public Sighting getSightingById(int sightingId) {

        try {
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), sightingId);

            sighting.setHero(getHeroForSighting(sightingId));
            sighting.setLocation(getLocationForSighting(sightingId));

            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Hero getHeroForSighting(int sightingId) {

        Hero hero = jdbc.queryForObject(GET_HERO_FOR_SIGHTING, new HeroMapper(), sightingId);

        hero.setPower(getPowerForHero(hero.getHeroId()));
        hero.setOrganizations(getOrganizationsForHero(hero.getHeroId()));

        return hero;
    }

    private Power getPowerForHero(int heroId) {

        return jdbc.queryForObject(GET_POWER_FOR_HERO, new PowerDaoDB.PowerMapper(),
                heroId);
    }

    private List<Organization> getOrganizationsForHero(int heroId) {

        return jdbc.query(GET_ORGANIZATION_FOR_HERO, new OrganizationDaoDB.OrganizationMapper(),
                heroId);
    }

    private Location getLocationForSighting(int sightingId) {

        return jdbc.queryForObject(GET_LOCATION_FOR_SIGHTING, new LocationMapper(), sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {

        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        associateHeroAndLocation(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getAll10RecentSightings() {

        List<Sighting> sightings = jdbc.query(GET_ALL_10_RECENT_SIGHTING, new SightingMapper());
        associateHeroAndLocation(sightings);
        return sightings;
    }

    private void associateHeroAndLocation(List<Sighting> sightings) {

        for (Sighting sighting : sightings) {
            sighting.setHero(getHeroForSighting(sighting.getSightingId()));
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {

        jdbc.update(ADD_SIGHTING,
                sighting.getHero().getHeroId(),
                sighting.getLocation().getLocationId(),
                sighting.getDescription(),
                sighting.getSightingDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {

        jdbc.update(UPDATE_SIGHTING,
                sighting.getHero().getHeroId(),
                sighting.getLocation().getLocationId(),
                sighting.getDescription(),
                sighting.getSightingDate(),
                sighting.getSightingId());
    }

    @Override
    public void deleteSightingById(int sightingId) {

        jdbc.update(DELETE_SIGHTING, sightingId);
    }

  
//     report all sightings (hero and location) for a particular date.
    @Override
    public List<Sighting> getSightingForDate(LocalDate sightingDateForSearch) {

        try {
            List<Sighting> sightings = jdbc.query(GET_SIGHTING_FOR_DATE, new SightingMapper(), sightingDateForSearch);
            associateHeroAndLocation(sightings);
            return sightings;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingForHero(int heroIdForSearch) {

        try {
            List<Sighting> sightings = jdbc.query(GET_SIGHTING_FOR_HERO, new SightingMapper(), heroIdForSearch);
            associateHeroAndLocation(sightings);
            return sightings;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setDescription(rs.getString("description"));
            sighting.setSightingDate(rs.getString("sightingDate"));

            return sighting;
        }
    }
}
