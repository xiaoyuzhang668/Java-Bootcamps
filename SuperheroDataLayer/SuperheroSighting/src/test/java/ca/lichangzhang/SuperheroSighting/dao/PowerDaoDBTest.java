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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author catzh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerDaoDBTest {

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

    public PowerDaoDBTest() {
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
            powerDao.deletePowerForTesting(power.getPowerId());
        }
    }

    /**
     * Test of getPowerById method, of class PowerDaoDB.
     */
    @Test
    public void testAddAndGetPower() {

        Power power = new Power();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
    }

    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {

        Power power = new Power();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setName("Test Power Name 2");
        power2.setDescription("Test Power Description 2");
        power2 = powerDao.addPower(power2);

        List<Power> powers = powerDao.getAllPowers();

        assertEquals(2, powers.size());
        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {

        Power power = new Power();
        power.setName("Test Power Name");
        power.setDescription("Test Power Description");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);

        power.setName("New Test Power Name");
        powerDao.updatePower(power);

        assertNotEquals(power, fromDao);

        fromDao = powerDao.getPowerById(power.getPowerId());

        assertEquals(power, fromDao);
    }

    /**
     * Test of deletePowerById method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePowerById() {

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

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);

        powerDao.deletePowerById(power.getPowerId());

        fromDao = powerDao.getPowerById(power.getPowerId());
        assertNull(fromDao);
    }

    @Test
    public void deletePowerForTesting() {

        Power power = new Power();
        power.setName("Test Superpower Name");
        power.setDescription("Test Superpower Description");
        power = powerDao.addPower(power);

        powerDao.deletePowerForTesting(power.getPowerId());

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertNull(fromDao);
    }

}
