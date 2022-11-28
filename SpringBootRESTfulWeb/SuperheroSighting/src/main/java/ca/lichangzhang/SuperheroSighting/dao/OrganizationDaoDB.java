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

    final String GET_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE organizationId = ?";
    final String GET_ALL_ORGANIZATION = "SELECT * FROM organization ORDER BY organizationId DESC";
    final String ADD_ORGANIZATION = "INSERT INTO organization(name, description, address, contact, phone) "
            + "VALUES(?, ?, ?, ?, ?)";
    final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, "
            + "address = ?, contact = ?, phone = ? WHERE organizationId = ?";
    final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE organizationId = ?";
    final String DELETE_ORGANIZATON = "DELETE FROM organization WHERE organizationId = ?";
    final String GET_ORGANIZATION_FOR_HERO = "SELECT O.* FROM organization AS O  "
            + "INNER JOIN hero_organization AS HO ON O.organizationId = HO.organizationId "
            + "INNER JOIN hero AS H ON HO.heroId = H.heroId WHERE H.heroId = ?";

    @Override
    public Organization getOrganizationById(int organizationId) {

        try {
            return jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), organizationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {

        return jdbc.query(GET_ALL_ORGANIZATION, new OrganizationMapper());
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {

        jdbc.update(ADD_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getPhone());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationId(newId);
        
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {

        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getPhone(),
                organization.getOrganizationId());
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int organizationId) {

        jdbc.update(DELETE_HERO_ORGANIZATION, organizationId);
        jdbc.update(DELETE_ORGANIZATON, organizationId);
    }

//     report all of the organizations a particular superhero/villain belongs to
    @Override
    public List<Organization> getOrganizationForHero(Hero hero) {

        List<Organization> organizations = jdbc.query(GET_ORGANIZATION_FOR_HERO,
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
            organization.setPhone(rs.getString("phone"));

            return organization;
        }
    }
}
