package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Location;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.service.HeroService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Controller
public class HeroController {

    @Autowired
    HeroService heroService;

    @GetMapping("heros")
    public String displayHeros(Model model) {

        List<Hero> heros = heroService.getAllHeros();
        model.addAttribute("heros", heros);

        return "heros";
    }

    @GetMapping("heros/addHero")
    public String displayAddHeros(Model model) {

        List<Power> powers = heroService.getAllPowers();
        List<Organization> organizations = heroService.getAllOrganizations();

        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("hero", new Hero());

        return "heros/addHero";
    }

    @PostMapping("heros/addHero")
    public String addHero(
            @Valid @ModelAttribute("hero") Hero hero,
            BindingResult result,
            Model model,
            int powerId,
            HttpServletRequest request,
            @RequestParam("photo") MultipartFile file
    ) throws IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Boolean existingE = false;

        if (name == null || name.length() == 0 || name == "") {
            model.addAttribute("heroNameMessage", "Hero name must not be empty.");
            existingE = true;
        }

        List<Hero> heroLists = heroService.getAllHeros();
        for (Hero heroList : heroLists) {
            if (name.toLowerCase().equals(heroList.getName().toLowerCase())) {
                model.addAttribute("heroNameMessage", "Hero name must not be duplicate.");
                existingE = true;
            }
        }

        if (description == null || description.length() == 0 || description == "") {
            model.addAttribute("heroDescriptionMessage", "Hero description must not be empty.");
            existingE = true;
        }

        if (powerId != 0) {
            hero.setPower(heroService.getPowerById(powerId));
        }

        String[] organizationIds = request.getParameterValues("organizationId");
        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(heroService.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            model.addAttribute("heroOrganizationMessage", "Hero organization must not be empty.");
            existingE = true;
        }

        hero.setOrganizations(organizations);

        if (file.getBytes().length > 125000) {
            model.addAttribute("heroPhotoMessage", "Hero photo must not be bigger than 125000bytes.");
            existingE = true;
        }

        if (!file.isEmpty()) {
            hero.setPhoto(file.getBytes());
        }

        if (existingE == false) {
            heroService.addHero(hero);
        } else {
            List<Power> powers = heroService.getAllPowers();
            List<Organization> allOrganizations = heroService.getAllOrganizations();

            model.addAttribute("powers", powers);
            model.addAttribute("organizations", allOrganizations);
            model.addAttribute("hero", new Hero());
            return "heros/addHero";
        }

        return "redirect:/heros";
    }

    @GetMapping("heros/heroDetail")
    public String heroDetail(
            Integer heroId,
            Model model) {

        Hero hero = heroService.getHeroById(heroId);
        List<Organization> organizations = heroService.getOrganizationForHero(new Hero(heroId));
        List<Location> locations = heroService.getLocationForHero(new Hero(heroId));

        model.addAttribute("hero", hero);
        model.addAttribute("organizations", organizations);
        model.addAttribute("locations", locations);

        return "heros/heroDetail";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer heroId) {

        heroService.deleteHeroById(heroId);
        return "redirect:/heros";
    }

    @GetMapping("heros/editHero")
    public String editHero(
            Integer heroId, 
            Model model) {

        Hero hero = heroService.getHeroById(heroId);
        List<Power> powers = heroService.getAllPowers();
        List<Organization> organizations = heroService.getAllOrganizations();

        model.addAttribute("hero", hero);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);

        return "heros/editHero";
    }

    @PostMapping("heros/editHero")
    public String performEditHero(
            @Valid @ModelAttribute("hero") Hero hero,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            @RequestParam("photo") MultipartFile file,
            @RequestParam("removeText") String removeText
    ) throws IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String powerId = request.getParameter("powerId");
        String[] organizationIds = request.getParameterValues("organizationId");
        Boolean existingE = false;

        if (name == null || name.length() == 0 || name == "") {
            model.addAttribute("heroNameMessage", "Hero name must not be empty.");
            existingE = true;
        }

        List<Hero> heroLists = heroService.getAllHeros();
        for (Hero heroList : heroLists) {
            if  ((heroList.getHeroId() != hero.getHeroId()) &&  (name.toLowerCase().equals(heroList.getName().toLowerCase())) ){
                model.addAttribute("heroNameMessage", "Hero name must not be duplicate.");
                existingE = true;
            }
        }

        if (description == null || description.length() == 0 || description == "") {
            model.addAttribute("heroDescriptionMessage", "Hero description must not be empty.");
            existingE = true;
        }

        if (powerId != "0") {
            hero.setPower(heroService.getPowerById(Integer.parseInt(powerId)));
        }

        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(heroService.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            model.addAttribute("heroOrganizationMessage", "Hero organization must not be empty.");
            existingE = true;
        }

        hero.setOrganizations(organizations);

        if (file.getBytes().length > 125000) {
            model.addAttribute("heroPhotoMessage", "Hero photo must not be bigger than 125000bytes.");
            existingE = true;
        }

        if (!file.isEmpty()) {
            hero.setPhoto(file.getBytes());
        }

        if (existingE == false) {
            if (!file.isEmpty()) {
                heroService.updateHero(hero);
            } else if ((file.isEmpty()) && removeText.length() > 0) {
                heroService.updateHero(hero);
            } else {
                heroService.updateHeroNoImage(hero);
            }
        } else {
            List<Power> powers = heroService.getAllPowers();
            List<Organization> allOrganizations = heroService.getAllOrganizations();

            model.addAttribute("powers", powers);
            model.addAttribute("organizations", allOrganizations);
            model.addAttribute("hero", hero);
            return "heros/editHero";
        }

        return "redirect:/heros";
    }
}
