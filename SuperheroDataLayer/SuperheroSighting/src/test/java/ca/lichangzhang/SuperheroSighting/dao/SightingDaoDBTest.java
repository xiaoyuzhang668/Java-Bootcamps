package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author catzh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {

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

    public SightingDaoDBTest() {
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

        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerForTesting(power.getPowerId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }
    }

    /**
     * Test of getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSighting() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
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
        BigDecimal latitude = new BigDecimal("232.343439999");
        location.setLatitude(latitude);
        BigDecimal longitude = new BigDecimal("453.324231234");
        location.setLongitude(longitude);
        location = locationDao.addLocation(location);

        LocalDate sightingDate = LocalDate.parse("2015-01-01");
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting Description");
        sighting.setSightingDate(sightingDate);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
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

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");

        BigDecimal latitude = new BigDecimal("232.343439999");
        location.setLatitude(latitude);

        BigDecimal longitude = new BigDecimal("453.324231234");
        location.setLongitude(longitude);
        location = locationDao.addLocation(location);

        LocalDate sightingDate = LocalDate.parse("2015-01-01");
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting Description");
        sighting.setSightingDate(sightingDate);
        sighting = sightingDao.addSighting(sighting);

        LocalDate sightingDate2 = LocalDate.parse("2022-01-01");
        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero2);
        sighting2.setLocation(location);
        sighting2.setDescription("Test Sighting Description 2");
        sighting2.setSightingDate(sightingDate2);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
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

        BigDecimal latitude = new BigDecimal("232.343439999");
        location.setLatitude(latitude);

        BigDecimal longitude = new BigDecimal("453.324231234");
        location.setLongitude(longitude);
        location = locationDao.addLocation(location);

        LocalDate sightingDate = LocalDate.parse("2015-01-01");
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting Description");
        sighting.setSightingDate(sightingDate);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);

        sighting.setDescription("New Test Sighting Description.");
        sightingDao.updateSighting(sighting);

        assertNotEquals(sighting, fromDao);

        fromDao = sightingDao.getSightingById(sighting.getSightingId());

        assertEquals(sighting, fromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
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

        BigDecimal latitude = new BigDecimal("232.343439999");
        location.setLatitude(latitude);

        BigDecimal longitude = new BigDecimal("453.324231234");
        location.setLongitude(longitude);
        location = locationDao.addLocation(location);

        LocalDate sightingDate = LocalDate.parse("2015-01-01");
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting Description");
        sighting.setSightingDate(sightingDate);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getSightingId());

        fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingForDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingForDate() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
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

        BigDecimal latitude = new BigDecimal("232.343439999");
        location.setLatitude(latitude);

        BigDecimal longitude = new BigDecimal("453.324231234");
        location.setLongitude(longitude);
        location = locationDao.addLocation(location);

        LocalDate sightingDate = LocalDate.parse("2015-01-01");
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting Description");
        sighting.setSightingDate(sightingDate);
        sighting = sightingDao.addSighting(sighting);

        List<Sighting> fromDate = sightingDao.getSightingForDate(sightingDate);

        assertEquals(1, fromDate.size());
        assertTrue(fromDate.contains(sighting));
    }
}
