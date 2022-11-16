package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dao.OrganizationDaoDB.OrganizationMapper;
import ca.lichangzhang.SuperheroSighting.dao.PowerDaoDB.PowerMapper;
import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    final String GET_HERO_BY_ID = "SELECT * FROM hero WHERE heroId = ?";
    final String GET_POWER_FOR_HERO = "SELECT P.* FROM power AS P "
            + "RIGHT OUTER JOIN hero AS H ON P.powerId = H.powerId WHERE H.heroId = ?";
    final String GET_ORGANIZATION_FOR_HERO = "SELECT O.* FROM organization AS O "
            + "LEFT OUTER JOIN hero_organization AS HO ON O.organizationId = HO.organizationId WHERE HO.heroId = ?";
    final String GET_ALL_HEROS = "SELECT * FROM hero ORDER BY heroId DESC";
    final String ADD_HERO = "INSERT INTO hero(name, isHero, description, photo, powerId) "
            + "VALUES(?,?,?,?,?)";
    final String INSERT_HERO_ORGANIZATION = "INSERT INTO "
            + "hero_organization(heroId, organizationId) VALUES(?,?)";
    final String UPDATE_HERO = "UPDATE hero SET name = ?, isHero = ?, description = ?, "
            + "photo = ?, powerId = ? WHERE heroId = ?";
    final String UPDATE_HERO_NO_IMAGE = "UPDATE hero SET name = ?, isHero = ?, description = ?, "
            + " powerId = ? WHERE heroId = ?";
    final String DELETE_FROM_SIGHTING = "DELETE FROM sighting WHERE heroId = ?";
    final String DELETE_FROM_HERO_ORGANIZATION_FOR_ID = "DELETE FROM hero_organization WHERE heroId = ?";
    final String DELETE_FROM_HERO = "DELETE FROM hero WHERE heroId = ?";
    final String GET_HERO_FOR_LOCATION = "SELECT DISTINCT H.* FROM hero AS H  "
            + "INNER JOIN Sighting AS S ON H.heroId = S.heroId "
            + "INNER JOIN Location AS L ON L.locationId = S.locationId WHERE L.locationId = ? ORDER BY H.name ASC";
    final String GET_HERO_FOR_ORGANIZATION = "SELECT H.* FROM hero AS H  "
            + "INNER JOIN hero_organization AS HO ON H.heroId = HO.heroId "
            + "INNER JOIN organization AS O ON HO.organizationId = O.organizationId WHERE O.organizationId = ? ORDER BY H.name ASC";
    final String DELETE_FROM_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE heroId = ?";

    @Override
    public Hero getHeroById(int heroId) {

        try {
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID,
                    new HeroMapper(), heroId);

            hero.setPower(getPowerForHero(heroId));
            hero.setOrganizations(getOrganizationsForHero(heroId));

            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Power getPowerForHero(int heroId) {

        try {
            return jdbc.queryForObject(GET_POWER_FOR_HERO, new PowerMapper(),
                    heroId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Organization> getOrganizationsForHero(int heroId) {

        return jdbc.query(GET_ORGANIZATION_FOR_HERO, new OrganizationMapper(),
                heroId);
    }

    @Override
    public List<Hero> getAllHeros() {

        List<Hero> heros = jdbc.query(GET_ALL_HEROS, new HeroMapper());
        associatePowerAndOrganizations(heros);
        return heros;
    }

    private void associatePowerAndOrganizations(List<Hero> heros) {
        for (Hero hero : heros) {
            hero.setPower(getPowerForHero(hero.getHeroId()));
            hero.setOrganizations(getOrganizationsForHero(hero.getHeroId()));
        }
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {

        jdbc.update(ADD_HERO,
                hero.getName(),
                hero.getIsHero(),
                hero.getDescription(),
                hero.getPhoto(),
                hero.getPower().getPowerId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        insertHeroOrganization(hero);
        return hero;
    }

    private void insertHeroOrganization(Hero hero) {

        for (Organization organization : hero.getOrganizations()) {
            jdbc.update(INSERT_HERO_ORGANIZATION,
                    hero.getHeroId(),
                    organization.getOrganizationId());
        }
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {

        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getIsHero(),
                hero.getDescription(),
                hero.getPhoto(),
                hero.getPower().getPowerId(),
                hero.getHeroId());

        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, hero.getHeroId());
        insertHeroOrganization(hero);
    }

    @Override
    @Transactional
    public void updateHeroNoImage(Hero hero) {

        jdbc.update(UPDATE_HERO_NO_IMAGE,
                hero.getName(),
                hero.getIsHero(),
                hero.getDescription(),
                hero.getPower().getPowerId(),
                hero.getHeroId());

        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, hero.getHeroId());
        insertHeroOrganization(hero);
    }

    @Override
    @Transactional
    public void deleteHeroById(int heroId) {

        jdbc.update(DELETE_FROM_SIGHTING, heroId);

        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, heroId);

        jdbc.update(DELETE_FROM_HERO, heroId);
    }

//     report all of the superheroes sighted at a particular location.
    @Override
    public List<Hero> getHeroForLocation(Location location) {

        List<Hero> heros = jdbc.query(GET_HERO_FOR_LOCATION,
                new HeroMapper(), location.getLocationId());
        associatePowerAndOrganizations(heros);
        return heros;
    }

    @Override
    public List<Hero> getHeroForOrganization(Organization organization) {

        List<Hero> heros = jdbc.query(GET_HERO_FOR_ORGANIZATION,
                new HeroMapper(), organization.getOrganizationId());
        associatePowerAndOrganizations(heros);
        return heros;
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("heroId"));
            hero.setName(rs.getString("name"));
            hero.setIsHero(rs.getBoolean("isHero"));
            hero.setDescription(rs.getString("description"));
            hero.setPhoto(rs.getBytes("photo"));

            return hero;
        }
    }
}
