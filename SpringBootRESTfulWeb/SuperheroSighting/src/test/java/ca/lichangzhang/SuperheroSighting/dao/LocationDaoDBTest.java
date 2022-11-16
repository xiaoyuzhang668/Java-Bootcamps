/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author catzh
 */
@SpringBootTest
public class LocationDaoDBTest {

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

    public LocationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }

        List<Hero> heros = heroDao.getAllHeros();
        for (Hero hero : heros) {
            heroDao.deleteHeroById(hero.getHeroId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getOrganizationId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }

        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerById(power.getPowerId());
        }
    }

    @Test
    public void testAddAndGetLocation() {

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setPhone("12345678");
        location.setLatitude("232.343439999");
        location.setLongitude("453.324231234");

        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setPhone("12345678");
        location.setLatitude("232.343439999");
        location.setLongitude("453.324231234");

        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setPhone("12345678");
        location2.setLatitude("232.343439999");
        location2.setLongitude("453.324231234");

        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setPhone("12345678");
        location.setLatitude("232.343439999");
        location.setLongitude("453.324231234");

        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);

        location.setName("New Test Name");
        locationDao.updateLocation(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getLocationId());

        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
        organization.setPhone("123456789");
        organization = organizationDao.addOrganization(organization);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Boolean value = true;
        Hero hero = new Hero();
        hero.setName("Test Superhero Name");
        hero.setIsHero(value);
        hero.setDescription("Test Superhero Description");
        hero.setPower(power);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setPhone("12345678");
        location.setLatitude("232.343439999");
        location.setLongitude("453.324231234");

        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting Description.");
        sighting.setSightingDate("2017-01-13T17:09:42");

        sighting = sightingDao.addSighting(sighting);

        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);

        locationDao.deleteLocationById(location.getLocationId());

        fromDao = locationDao.getLocationById(location.getLocationId());
        assertNull(fromDao);

        locationDao.deleteLocationForTesting();
    }

    @Test
    public void testGetLocationForHero() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
        organization.setPhone("123456789");
        organization = organizationDao.addOrganization(organization);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Boolean value = true;
        Hero hero = new Hero();
        hero.setName("Test Superhero Name");
        hero.setIsHero(value);
        hero.setDescription("Test Superhero Description");
        hero.setPower(power);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero2.setName("Test Superhero Name 2");
        hero2.setIsHero(value);
        hero2.setDescription("Test Superhero Description 2");
        hero2.setPower(power);
        hero2.setOrganizations(organizations);
        hero2 = heroDao.addHero(hero2);

        Double latitude = Double.parseDouble("232.343439999");
        Double longitude = Double.parseDouble("453.324231234");

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setPhone("12345678");
        location.setLatitude("232.343439999");
        location.setLongitude("453.324231234");
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setPhone("12345678");
        location2.setLatitude("232.343439999");
        location2.setLongitude("453.324231234");
        location2 = locationDao.addLocation(location2);

        Location location3 = new Location();
        location3.setName("Test Name 3");
        location3.setDescription("Test Description 3");
        location3.setAddress("Test Address 3");
        location3.setPhone("12345678");
        location3.setLatitude("232.343439999");
        location3.setLongitude("453.324231234");
        location3 = locationDao.addLocation(location3);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting Description.");
        sighting.setSightingDate("2017-01-13T17:09:42");
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero2);
        sighting2.setLocation(location2);
        sighting2.setDescription("Test Sighting Description 2.");
        sighting2.setSightingDate("2017-01-13T17:09:42");
        sighting2 = sightingDao.addSighting(sighting2);

        Sighting sighting3 = new Sighting();
        sighting3.setHero(hero);
        sighting3.setLocation(location3);
        sighting3.setDescription("Test Sighting Description 3.");
        sighting3.setSightingDate("2017-01-13T17:09:42");
        sighting3 = sightingDao.addSighting(sighting3);

        List<Location> locations = locationDao.getLocationForHero(hero);

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertFalse(locations.contains(location2));
        assertTrue(locations.contains(location3));
    }
}
