package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            final String sql = "SELECT * FROM organization WHERE organizationId = ?";
            return jdbc.queryForObject(sql, new OrganizationMapper(), organizationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String sql = "SELECT * FROM organization";
        return jdbc.query(sql, new OrganizationMapper());
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String sql = "INSERT INTO organization(name, description, address, contact) "
                + "VALUES(?, ?, ?, ?)";
        jdbc.update(sql,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationId(newId);
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String sql = "UPDATE organization SET name = ?, description = ?, "
                + "address = ?, contact = ? WHERE organizationId = ?";
        jdbc.update(sql,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getOrganizationId());
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int organizationId) {

        final String sql = "DELETE FROM hero_organization WHERE organizationId = ?";
        jdbc.update(sql, organizationId);

        final String sql2 = "DELETE FROM organization WHERE organizationId = ?";
        jdbc.update(sql2, organizationId);
    }

//     report all of the organizations a particular superhero/villain belongs to
    @Override
    public List<Organization> getOrganizationForHero(Hero hero) {
        final String sql = "SELECT O.* FROM organization AS O  "
                + "INNER JOIN hero_organization AS HO ON O.organizationId = HO.organizationId "
                + "INNER JOIN hero AS H ON HO.heroId = H.heroId WHERE H.heroId = ?";
        List<Organization> organizations = jdbc.query(sql,
                new OrganizationMapper(), hero.getHeroId());
        return organizations;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationId(rs.getInt("organizationId"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setContact(rs.getString("contact"));

            return organization;
        }
    }
}
