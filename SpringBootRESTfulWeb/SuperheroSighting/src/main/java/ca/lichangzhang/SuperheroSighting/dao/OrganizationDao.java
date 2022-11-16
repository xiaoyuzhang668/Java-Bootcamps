package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import java.util.List;

/**　Email: xiaoyuzhang668@gmail.com
 *   Date: 2022
 *
 *  @author catzh
 */
public interface OrganizationDao {
    
    Organization getOrganizationById(int organizationId);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int organizationId);
    
//     report all of the organizations a particular superhero/villain belongs to
    List<Organization> getOrganizationForHero(Hero hero);
}
