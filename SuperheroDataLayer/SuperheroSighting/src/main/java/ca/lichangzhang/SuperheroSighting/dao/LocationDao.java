package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import java.util.List;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public interface LocationDao {

    Location getLocationById(int locationId);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int locationId);

    void deleteLocationForTesting();

//    report all of the locations where a particular superhero has been seen.
    List<Location> getLocationForHero(Hero hero);
}
