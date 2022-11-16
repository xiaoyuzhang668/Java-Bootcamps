package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.service.HeroService;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            HttpServletRequest request,
            @RequestParam("photo") MultipartFile file
    ) throws IOException, SQLException {

        String powerId = request.getParameter("powerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        hero.setPower(heroService.getPowerById(Integer.parseInt(powerId)));

        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(heroService.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("hero", "organizations", "Hero entry must include at least one organization.");
            result.addError(error);
        }

        hero.setOrganizations(organizations);
        hero.setPhoto(file.getBytes());
        
        if (result.hasErrors()) {
            model.addAttribute("powers", heroService.getAllPowers());
            model.addAttribute("organizations", heroService.getAllOrganizations());
            model.addAttribute("hero", hero);

            return "heros/addHero";
        }

        heroService.addHero(hero);

        return "redirect:/heros";
    }

    @GetMapping("heros/heroDetail")
    public String heroDetail(Integer heroId, Model model) {

        Hero hero = heroService.getHeroById(heroId);
        byte[] heroImage = hero.getPhoto();

        model.addAttribute("hero", hero);
   
        return "heros/heroDetail";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer heroId) {

        heroService.deleteHeroById(heroId);
        return "redirect:/heros";
    }

    @GetMapping("heros/editHero")
    public String editHero(Integer heroId, Model model) {

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


//        try {
//            hero.setPhoto(file.getBytes());
//        } catch (IOException ex) {
//            FieldError error = new FieldError("hero", "photo", "Error with photo uploading, please try later.");
//            result.addError(error);
//        }
        hero.setPhoto(file.getBytes());
        String powerId = request.getParameter("powerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        hero.setPower(heroService.getPowerById(Integer.parseInt(powerId)));
        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(heroService.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("hero", "organizations", "Hero entry must include at least one organization.");
            result.addError(error);
        }

        hero.setOrganizations(organizations);
//        hero.setPhoto(file.getBytes());

//        if (result.hasErrors()) {
//            model.addAttribute("powers", heroService.getAllPowers());
//            model.addAttribute("organizations", heroService.getAllOrganizations());
//            model.addAttribute("hero", hero);
//
//            return "heros/editHero";
//        }
        if (!file.isEmpty()) {
            heroService.updateHero(hero);
        } else if ((file.isEmpty()) && removeText.length() > 0) {
            heroService.updateHero(hero);
        } else {
            heroService.updateHeroNoImage(hero);
        }
        return "redirect:/heros";
    }
}
