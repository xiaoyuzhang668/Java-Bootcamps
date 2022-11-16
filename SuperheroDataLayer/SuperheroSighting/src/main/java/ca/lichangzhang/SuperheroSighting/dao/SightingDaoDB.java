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

    @Override
    public Sighting getSightingById(int sightingId) {
        try {
            final String sql = "SELECT * FROM sighting WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(sql, new SightingMapper(), sightingId);

            sighting.setHero(getHeroForSighting(sightingId));
            sighting.setLocation(getLocationForSighting(sightingId));

            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Hero getHeroForSighting(int sightingId) {
        final String sql = "SELECT H.* FROM hero AS H "
                + "INNER JOIN sighting AS S ON S.heroId = H.heroId WHERE S.sightingId = ?";
        Hero hero = jdbc.queryForObject(sql, new HeroMapper(), sightingId);

        hero.setPower(getPowerForHero(hero.getHeroId()));
        hero.setOrganizations(getOrganizationsForHero(hero.getHeroId()));
        
        return hero;
    }

    private Power getPowerForHero(int heroId) {
        final String sql = "SELECT P.* FROM power AS P "
                + "INNER JOIN hero AS H ON P.powerId = H.powerId WHERE H.heroId = ?";
        return jdbc.queryForObject(sql, new PowerDaoDB.PowerMapper(),
                heroId);
    }

    private List<Organization> getOrganizationsForHero(int heroId) {
        final String sql = "SELECT O.* FROM organization AS O "
                + "INNER JOIN hero_organization AS HO ON O.organizationId = HO.organizationId WHERE HO.heroId = ?";
        return jdbc.query(sql, new OrganizationDaoDB.OrganizationMapper(),
                heroId);
    }

    private Location getLocationForSighting(int sightingId) {
        final String sql = "SELECT L.* FROM location AS L "
                + "INNER JOIN sighting AS S ON S.locationId = L.locationId WHERE S.sightingId = ?";
        return jdbc.queryForObject(sql, new LocationMapper(), sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String sql = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(sql, new SightingMapper());
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
        final String sql = "INSERT INTO sighting(heroId, locationId, description, sightingDate) "
                + "VALUES(?,?,?,?)";
        jdbc.update(sql,
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
        final String sql = "UPDATE sighting SET heroId = ?, locationId = ?, description = ?, "
                + "sightingDate = ? WHERE sightingId = ?";
        jdbc.update(sql,
                sighting.getHero().getHeroId(),
                sighting.getLocation().getLocationId(),
                sighting.getDescription(),
                sighting.getSightingDate(),
                sighting.getSightingId());
    }

    @Override
    public void deleteSightingById(int sightingId) {
        final String sql = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(sql, sightingId);
    }

//     report all sightings (hero and location) for a particular date.
    @Override
    public List<Sighting> getSightingForDate(LocalDate sightingDate) {
        try {
            final String sql = "SELECT * FROM sighting WHERE sightingDate = ?";
            List<Sighting> sightings = jdbc.query(sql, new SightingMapper(), sightingDate);
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
            sighting.setSightingDate(rs.getDate("sightingDate").toLocalDate());

            return sighting;
        }
    }
}
