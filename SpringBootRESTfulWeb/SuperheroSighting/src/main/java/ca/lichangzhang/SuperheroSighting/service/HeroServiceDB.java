package ca.lichangzhang.SuperheroSighting.service;

import ca.lichangzhang.SuperheroSighting.dao.HeroDao;
import ca.lichangzhang.SuperheroSighting.dao.LocationDao;
import ca.lichangzhang.SuperheroSighting.dao.OrganizationDao;
import ca.lichangzhang.SuperheroSighting.dao.PowerDao;
import ca.lichangzhang.SuperheroSighting.dao.SightingDao;
import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Repository
public class HeroServiceDB implements HeroService {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    PowerDao powerDao;

    @Autowired
    SightingDao sightingDao;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//    HERO
    @Override
    public Hero getHeroById(int heroId) {
        return heroDao.getHeroById(heroId);
    }

    @Override
    public List<Hero> getAllHeros() {
        return heroDao.getAllHeros();
    }

    @Override
    public Hero addHero(Hero hero) {
        return heroDao.addHero(hero);
    }

    @Override
    public void updateHero(Hero hero) {
        heroDao.updateHero(hero);
    }

    @Override
    public void updateHeroNoImage(Hero hero) {
        heroDao.updateHeroNoImage(hero);
    }

    @Override
    public void deleteHeroById(int heroId) {
        heroDao.deleteHeroById(heroId);
    }

    @Override
    public List<Hero> getHeroForLocation(Location location) {
        return heroDao.getHeroForLocation(location);
    }

    @Override
    public List<Hero> getHeroForOrganization(Organization organization) {
        return heroDao.getHeroForOrganization(organization);
    }

//    LOCATION
    @Override
    public Location getLocationById(int locationId) {
        return locationDao.getLocationById(locationId);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location addLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public void deleteLocationById(int locationId) {
        locationDao.deleteLocationById(locationId);
    }

    @Override
    public void deleteLocationForTesting() {
        locationDao.deleteLocationForTesting();
    }

    @Override
    public List<Location> getLocationForHero(Hero hero) {
        return locationDao.getLocationForHero(hero);
    }

//    ORGANIZATION
    @Override
    public Organization getOrganizationById(int organizationId) {
        return organizationDao.getOrganizationById(organizationId);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }

    @Override
    public void deleteOrganizationById(int organizationId) {
        organizationDao.deleteOrganizationById(organizationId);
    }

    @Override
    public List<Organization> getOrganizationForHero(Hero hero) {
        return organizationDao.getOrganizationForHero(hero);
    }

//    POWER
    @Override
    public Power getPowerById(int powerId) {
        return powerDao.getPowerById(powerId);
    }

    @Override
    public List<Power> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public Power addPower(Power power) {
        return powerDao.addPower(power);
    }

    @Override
    public void updatePower(Power power) {
        powerDao.updatePower(power);
    }

    @Override
    public void deletePowerById(int powerId) {
        powerDao.deletePowerById(powerId);
    }

//    SIGHTING
    @Override
    public Sighting getSightingById(int sightingId) {
        return sightingDao.getSightingById(sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingDao.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightingDao.updateSighting(sighting);
    }

    @Override
    public void deleteSightingById(int sightingId) {
        sightingDao.deleteSightingById(sightingId);
    }

    @Override
    public List<Sighting> getAll10RecentSightings() {
        return sightingDao.getAll10RecentSightings();
    }

    @Override
    public List<Sighting> getSightingForDate(LocalDate sightingDateForSearch) {
        return sightingDao.getSightingForDate(sightingDateForSearch);
    }

    @Override
    public List<Sighting> getSightingForHero(int heroIdForSearch) {
        return sightingDao.getSightingForHero(heroIdForSearch);
    }
}
