package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.service.SuperHeroNullException;
import java.util.List;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public interface HeroDao {

    Hero getHeroById(int heroId);

    List<Hero> getAllHeros();

    Hero addHero(Hero hero) throws SuperHeroNullException;

    void updateHero(Hero hero);

    void updateHeroNoImage(Hero hero);

    void deleteHeroById(int heroId);

// report all of the superheroes sighted at a particular location.
    List<Hero> getHeroForLocation(Location location);

//    report all of the members of a particular organization.
    List<Hero> getHeroForOrganization(Organization organization);
}
