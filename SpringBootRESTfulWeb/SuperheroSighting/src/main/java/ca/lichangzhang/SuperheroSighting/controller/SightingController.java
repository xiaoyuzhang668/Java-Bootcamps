package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Sighting;
import ca.lichangzhang.SuperheroSighting.service.HeroService;
import ca.lichangzhang.SuperheroSighting.service.SuperHeroNullException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Controller
public class SightingController {

    @Autowired
    HeroService heroService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("sightings")
    public String displaySightings(Model model) {

        List<Sighting> sightings = heroService.getAllSightings();
        List<Hero> heros = heroService.getAllHeros();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heros", heros);
        return "sightings";
    }

    @GetMapping("sightings/addSighting")
    public String displayAddSightings(Model model) {

        List<Hero> heros = heroService.getAllHeros();
        List<Location> locations = heroService.getAllLocations();

        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);
        model.addAttribute("sighting", new Sighting());

        return "sightings/addSighting";
    }

    @PostMapping("sightings/addSighting")
    public String addSighting(
            @Valid @ModelAttribute("sighting") Sighting sighting,
            BindingResult result,
            Model model,
            int heroId,
            int locationId,
            HttpServletRequest request) {

        String sightingDate = request.getParameter("sightingDate");

        if (heroId != 0) {
            sighting.setHero(heroService.getHeroById(heroId));
        } else {
            FieldError error = new FieldError("sighting", "hero", "Hero name must not be empty.");
            result.addError(error);
        }

        if (locationId != 0) {
            sighting.setLocation(heroService.getLocationById(locationId));
        } else {
            FieldError error = new FieldError("sighting", "location", "Location name must not be empty.");
            result.addError(error);
        }

        if (sightingDate != null && !sightingDate.isBlank()) {
            LocalDateTime sightingD = sighting.getFormatted();
            LocalDateTime currentTime = LocalDateTime.now();
            long diff = ChronoUnit.MINUTES.between(currentTime, sightingD);
            if (diff > 0) {
                FieldError error = new FieldError("sighting", "sightingDate", "Sighting date must not be in the future time.");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {

            List<Hero> heros = heroService.getAllHeros();
            List<Location> locations = heroService.getAllLocations();

            model.addAttribute("heros", heroService.getAllHeros());
            model.addAttribute("locations", heroService.getAllLocations());
            model.addAttribute("sighting", sighting);

            return "sightings/addSighting";
        }

        heroService.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("sightings/sightingDetail")
    public String sightingDetail(
            Integer sightingId,
            Model model) {

        Sighting sighting = heroService.getSightingById(sightingId);
        model.addAttribute("sighting", sighting);

        return "sightings/sightingDetail";
    }

    @GetMapping("sightings/editSighting")
    public String editSighting(
            Integer sightingId,
            Model model) {

        Sighting sighting = heroService.getSightingById(sightingId);
        List<Hero> heros = heroService.getAllHeros();
        List<Location> locations = heroService.getAllLocations();

        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);
        model.addAttribute("sighting", sighting);

        return "sightings/editSighting";
    }

    @PostMapping("sightings/editSighting")
    public String performEditSighting(
            @Valid @ModelAttribute("sighting") Sighting sighting,
            BindingResult result,
            Model model,
            HttpServletRequest request)
            throws SuperHeroNullException {

        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        String sightingDate = request.getParameter("sightingDate");

        sighting.setHero(heroService.getHeroById(Integer.parseInt(heroId)));
        sighting.setLocation(heroService.getLocationById(Integer.parseInt(locationId)));

        if (sightingDate != null && !sightingDate.isBlank()) {
            LocalDateTime sightingD = sighting.getFormatted();
            LocalDateTime currentTime = LocalDateTime.now();
            long diff = ChronoUnit.MINUTES.between(currentTime, sightingD);
            if (diff > 0) {
                FieldError error = new FieldError("sighting", "sightingDate", "Sighting date must not be in the future time.");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {

            model.addAttribute("heros", heroService.getAllHeros());
            model.addAttribute("locations", heroService.getAllLocations());
            model.addAttribute("sighting", sighting);

            return "sightings/editSighting";
        }

        heroService.updateSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("/sightings/searchSightingByDate")
    public String getSightingForDate(
            Sighting sighting,
            String sightingDateForSearch,
            Model model) throws SuperHeroNullException {

        List<Sighting> sightings = new ArrayList<>();

        if (sightingDateForSearch == null) {
            sightings = heroService.getAllSightings();
        } else {
            sightings = heroService.getSightingForDate(LocalDate.parse(sightingDateForSearch, formatter2));
        }
        model.addAttribute("sightings", sightings);
        return "sightings/searchSightingByDate";
    }

    @GetMapping("sightings/searchSightingByHero")
    public String getSightingForHero(
            Sighting sighting,
            int heroIdForSearch,
            Model model) {

        List<Sighting> sightings = new ArrayList<>();

        if (heroIdForSearch == 0) {
            sightings = heroService.getAllSightings();
        } else {
            sightings = heroService.getSightingForHero(heroIdForSearch);
        }

        model.addAttribute("sightings", sightings);

        return "sightings/searchSightingByHero";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer sightingId) {

        heroService.deleteSightingById(sightingId);
        return "redirect:/sightings";
    }

}
