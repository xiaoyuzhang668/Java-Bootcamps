package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.service.HeroService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Controller
public class LocationController {

    @Autowired
    HeroService heroService;

    @GetMapping("locations")
    public String displayLocations(Model model) {

        List<Location> locations = heroService.getAllLocations();
        model.addAttribute("locations", locations);

        return "locations";
    }

    @GetMapping("locations/addLocation")
    public String displayAddLocations(Model model) {

        model.addAttribute("location", new Location());

        return "locations/addLocation";
    }

    @PostMapping("locations/addLocation")
    public String addLocation(
            @Valid Location location,
            BindingResult result,
            HttpServletRequest request,
            Model model) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setPhone(phone);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        model.addAttribute("location", location);

        List<Location> locationLists = heroService.getAllLocations();
        for (Location locationList : locationLists) {
            if (name.toLowerCase().equals(locationList.getName().toLowerCase())) {
                FieldError error = new FieldError("location", "name", "Location name must not be duplicate.");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {
            return "locations/addLocation";
        }

        heroService.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {

        int locationId = Integer.parseInt(request.getParameter("locationId"));
        heroService.deleteLocationById(locationId);

        return "redirect:/locations";
    }

    @GetMapping("locations/editLocation")
    public String editLocation(HttpServletRequest request, Model model) {

        int locationId = Integer.parseInt(request.getParameter("locationId"));
        Location location = heroService.getLocationById(locationId);

        model.addAttribute("location", location);
        return "locations/editLocation";
    }

    @PostMapping("locations/editLocation")
    public String performEditLocation(
            @Valid Location location,
            BindingResult result,
            Model model,
            HttpServletRequest request) {
        
        String name = request.getParameter("name");
        List<Location> locationLists = heroService.getAllLocations();
        for (Location locationList : locationLists) {
            if ((locationList.getLocationId() != location.getLocationId() ) && (name.toLowerCase().equals(locationList.getName().toLowerCase()))) {
                FieldError error = new FieldError("location", "name", "Location name must not be duplicate.");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {
            return "locations/editLocation";
        }

        int locationId = Integer.parseInt(request.getParameter("locationId"));
        location = heroService.getLocationById(locationId);

        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setPhone(request.getParameter("phone"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));

        heroService.updateLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("locations/locationDetail")
    public String locationDetail(
            Integer locationId,
            Model model) {

        Location location = heroService.getLocationById(locationId);
        List<Hero> heros = heroService.getHeroForLocation(new Location(locationId));
        model.addAttribute("location", location);
        model.addAttribute("heros", heros);

        return "locations/locationDetail";
    }
}
