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
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
public class HeroDaoDBTest {

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

    public HeroDaoDBTest() {
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
     * Test of getHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHero() {

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

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());
        assertEquals(hero, fromDao);
    }

    /**
     * Test of getAllHeros method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeros() {

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

        List<Hero> heros = heroDao.getAllHeros();

        assertEquals(2, heros.size());
        assertTrue(heros.contains(hero));
        assertTrue(heros.contains(hero2));
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {

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

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());
        assertEquals(hero, fromDao);

        hero.setName("New Test Superhero Name");
        heroDao.updateHero(hero);

        assertNotEquals(hero, fromDao);

        fromDao = heroDao.getHeroById(hero.getHeroId());

        assertEquals(hero, fromDao);
    }

    /**
     * Test of deleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroById() {

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

        Hero fromDao = heroDao.getHeroById(hero.getHeroId());
        assertEquals(hero, fromDao);

        heroDao.deleteHeroById(hero.getHeroId());

        fromDao = heroDao.getHeroById(hero.getHeroId());
        assertNull(fromDao);
    }

    /**
     * Test of getHeroForLocation method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroForLocation() {

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

        Hero hero3 = new Hero();
        hero3.setName("Test Superhero Name 3");
        hero3.setIsHero(value);
        hero3.setDescription("Test Superhero Description 3");
        hero3.setPower(power);
        hero3.setOrganizations(organizations);
        hero3 = heroDao.addHero(hero3);

        BigDecimal latitude = new BigDecimal("232.343439999");
        BigDecimal longitude = new BigDecimal("453.324231234");

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setLatitude(latitude);
        location2.setLongitude(longitude);
        location2 = locationDao.addLocation(location2);

        LocalDate sightingDate = LocalDate.parse("2015-01-01");
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription("Sighting description");
        sighting.setSightingDate(sightingDate);
        sighting = sightingDao.addSighting(sighting);

        LocalDate sightingDate2 = LocalDate.parse("2018-01-01");
        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero2);
        sighting2.setLocation(location2);
        sighting2.setDescription("Sighting description 2");
        sighting2.setSightingDate(sightingDate2);
        sighting2 = sightingDao.addSighting(sighting2);

        LocalDate sightingDate3 = LocalDate.parse("2020-01-01");
        Sighting sighting3 = new Sighting();
        sighting3.setHero(hero);
        sighting3.setLocation(location2);
        sighting3.setDescription("Sighting description 3");
        sighting3.setSightingDate(sightingDate);
        sighting3 = sightingDao.addSighting(sighting3);

        List<Hero> heros = heroDao.getHeroForLocation(location2);

        assertEquals(2, heros.size());
        assertTrue(heros.contains(hero));
        assertTrue(heros.contains(hero2));
        assertFalse(heros.contains(hero3));
    }

    /**
     * Test of getHeroForOrganization method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroForOrganization() {

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

        Organization organization2 = new Organization();
        organization2.setName("Test Organization Name 2");
        organization2.setDescription("Test Organization Description 2");
        organization2.setAddress("Test Organization Address 2");
        organization2.setContact("Test Organization Contact 2");
        organization2 = organizationDao.addOrganization(organization2);

        Organization organization3 = new Organization();
        organization3.setName("Test Organization Name 3");
        organization3.setDescription("Test Organization Description 3");
        organization3.setAddress("Test Organization Address 3");
        organization3.setContact("Test Organization Contact 3");
        organization3 = organizationDao.addOrganization(organization3);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        List<Organization> organizations2 = new ArrayList<>();
        organizations2.add(organization);
        organizations2.add(organization3);

        List<Organization> organizations3 = new ArrayList<>();
        organizations3.add(organization2);
        organizations3.add(organization3);

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
        hero2.setOrganizations(organizations2);
        hero2 = heroDao.addHero(hero2);

        Hero hero3 = new Hero();
        hero3.setName("Test Superhero Name 3");
        hero3.setIsHero(value);
        hero3.setDescription("Test Superhero Description 3");
        hero3.setPower(power);
        hero3.setOrganizations(organizations3);
        hero3 = heroDao.addHero(hero3);

        List<Hero> heros = heroDao.getHeroForOrganization(organization);

        assertEquals(2, heros.size());
        assertTrue(heros.contains(hero));
        assertTrue(heros.contains(hero2));
        assertFalse(heros.contains(hero3));
    }
}
