package ca.lichangzhang.SuperheroSighting.service;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public interface HeroService {
//HERO    

    Hero getHeroById(int heroId);

    List<Hero> getAllHeros();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void updateHeroNoImage(Hero hero);

    void deleteHeroById(int heroId);

    List<Hero> getHeroForLocation(Location location);

    List<Hero> getHeroForOrganization(Organization organization);

//    LOCATION
    Location getLocationById(int locationId);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int locationId);

    void deleteLocationForTesting();

    List<Location> getLocationForHero(Hero hero);

//    ORGANIZATION
    Organization getOrganizationById(int organizationId);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int organizationId);

    List<Organization> getOrganizationForHero(Hero hero);

//    POWER
    Power getPowerById(int powerId);

    List<Power> getAllPowers();

    Power addPower(Power power);

    void updatePower(Power power);

    void deletePowerById(int powerId);

//    SIGHTING
    Sighting getSightingById(int sightingId);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting) throws
             SuperHeroNullException;

    void updateSighting(Sighting sighting)throws
              SuperHeroNullException;

    void deleteSightingById(int sightingId);

    List<Sighting> getAll10RecentSightings();

    List<Sighting> getSightingForDate(LocalDate sightingDateForSearch) throws
              SuperHeroNullException;
    
     List<Sighting> getSightingForHero(int heroIdForSearch);

}
