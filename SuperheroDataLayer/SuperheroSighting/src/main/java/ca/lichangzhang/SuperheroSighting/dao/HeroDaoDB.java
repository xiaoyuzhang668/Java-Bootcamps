package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dao.OrganizationDaoDB.OrganizationMapper;
import ca.lichangzhang.SuperheroSighting.dao.PowerDaoDB.PowerMapper;
import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroById(int heroId) {
        try {
            final String sql = "SELECT * FROM hero WHERE heroId = ?";
            Hero hero = jdbc.queryForObject(sql,
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
            final String sql = "SELECT P.* FROM power AS P "
                    + "INNER JOIN hero AS H ON P.powerId = H.powerId WHERE H.heroId = ?";
            return jdbc.queryForObject(sql, new PowerMapper(),
                    heroId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Organization> getOrganizationsForHero(int heroId) {
        final String sql = "SELECT O.* FROM organization AS O "
                + "INNER JOIN hero_organization AS HO ON O.organizationId = HO.organizationId WHERE HO.heroId = ?";
        return jdbc.query(sql, new OrganizationMapper(),
                heroId);
    }

    @Override
    public List<Hero> getAllHeros() {
        final String sql = "SELECT * FROM hero";
        List<Hero> heros = jdbc.query(sql, new HeroMapper());
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
        final String sql = "INSERT INTO hero(name, isHero, description, powerId) "
                + "VALUES(?,?,?,?)";
        jdbc.update(sql,
                hero.getName(),
                hero.getIsHero(),
                hero.getDescription(),
                hero.getPower().getPowerId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        insertHeroOrganization(hero);
        return hero;
    }

    private void insertHeroOrganization(Hero hero) {
        final String sql = "INSERT INTO "
                + "hero_organization(heroId, organizationId) VALUES(?,?)";
        for (Organization organization : hero.getOrganizations()) {
            jdbc.update(sql,
                    hero.getHeroId(),
                    organization.getOrganizationId());
        }
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        final String sql = "UPDATE hero SET name = ?, isHero = ?, description = ?, "
                + "powerId = ? WHERE heroId = ?";
        jdbc.update(sql,
                hero.getName(),
                hero.getIsHero(),
                hero.getDescription(),
                hero.getPower().getPowerId(),
                hero.getHeroId());

        final String sql2 = "DELETE FROM hero_organization WHERE heroId = ?";
        jdbc.update(sql2, hero.getHeroId());
        insertHeroOrganization(hero);
    }

    @Override
    @Transactional
    public void deleteHeroById(int heroId) {

        final String sql = "DELETE FROM sighting WHERE heroId = ?";
        jdbc.update(sql, heroId);

        final String sql2 = "DELETE FROM hero_organization WHERE heroId = ?";
        jdbc.update(sql2, heroId);

        final String sql3 = "DELETE FROM hero WHERE heroId = ?";
        jdbc.update(sql3, heroId);
    }

//     report all of the superheroes sighted at a particular location.
    @Override
    public List<Hero> getHeroForLocation(Location location) {
        final String sql = "SELECT H.* FROM hero AS H  "
                + "INNER JOIN Sighting AS S ON H.heroId = S.heroId "
                + "INNER JOIN Location AS L ON L.locationId = S.locationId WHERE L.locationId = ?";
        List<Hero> heros = jdbc.query(sql,
                new HeroMapper(), location.getLocationId());
        associatePowerAndOrganizations(heros);
        return heros;
    }

    @Override
    public List<Hero> getHeroForOrganization(Organization organization) {
        final String sql = "SELECT H.* FROM hero AS H  "
                + "INNER JOIN hero_organization AS HO ON H.heroId = HO.heroId "
                + "INNER JOIN organization AS O ON HO.organizationId = O.organizationId WHERE O.organizationId = ?";
        List<Hero> heros = jdbc.query(sql,
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

            return hero;
        }
    }
}
