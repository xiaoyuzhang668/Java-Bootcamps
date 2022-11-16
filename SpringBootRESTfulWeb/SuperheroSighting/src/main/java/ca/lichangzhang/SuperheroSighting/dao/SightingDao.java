package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public interface SightingDao {

    Sighting getSightingById(int sightingId);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int sightingId);

    List<Sighting> getAll10RecentSightings();

    List<Sighting> getSightingForDate(LocalDate sightingDateForSearch);

    List<Sighting> getSightingForHero(int heroId);
}
