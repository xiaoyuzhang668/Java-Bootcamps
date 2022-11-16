package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import ca.lichangzhang.SuperheroSighting.service.HeroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Controller
public class HomeController {

    @Autowired
    HeroService heroService;

    @GetMapping("/")
    public String getAll10RecentSightings(Model model) {

        List<Sighting> sightings = heroService.getAll10RecentSightings();
        List<Hero> heros = heroService.getAllHeros();
        List<Location> locations = heroService.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);    
        return "/index";
    }
}
