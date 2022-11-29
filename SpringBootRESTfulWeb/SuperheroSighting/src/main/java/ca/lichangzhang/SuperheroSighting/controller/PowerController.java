package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
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
public class PowerController {

    @Autowired
    HeroService heroService;

    @GetMapping("powers")
    public String displayPowers(Model model) {

        List<Power> powers = heroService.getAllPowers();
        model.addAttribute("powers", powers);
        return "powers";
    }

    @GetMapping("powers/addPower")
    public String displayAddPowers(Model model) {

        model.addAttribute("power", new Power());
        return "powers/addPower";
    }

    @PostMapping("powers/addPower")
    public String addPower(
            @Valid Power power,
            BindingResult result,
            HttpServletRequest request,
            Model model
    ) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        power.setName(name);
        power.setDescription(description);

        model.addAttribute("power", power);

        List<Power> powerLists = heroService.getAllPowers();
        for (Power powerList : powerLists) {
            if (name.toLowerCase().equals(powerList.getName().toLowerCase())) {
                FieldError error = new FieldError("power", "name", "Power name must not be duplicate.");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {
            return "powers/addPower";
        }

        heroService.addPower(power);

        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(Integer powerId) {

        heroService.deletePowerById(powerId);

        return "redirect:/powers";
    }

    @GetMapping("powers/editPower")
    public String editPower(
            Integer powerId,
            Model model) {

        Power power = heroService.getPowerById(powerId);
        model.addAttribute("power", power);

        return "powers/editPower";
    }

    @PostMapping("powers/editPower")
    public String performEditPower(
            @Valid Power power,
            HttpServletRequest request,
            BindingResult result) {

        List<Power> powerLists = heroService.getAllPowers();
        String name = request.getParameter("name");
        for (Power powerList : powerLists) {
            if ((powerList.getPowerId() != power.getPowerId() ) && (name.toLowerCase().equals(powerList.getName().toLowerCase()))) {
                FieldError error = new FieldError("power", "name", "Power name must not be duplicate.");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {
            return "powers/editPower";
        }

        heroService.updatePower(power);

        return "redirect:/powers";
    }

    @GetMapping("powers/powerDetail")
    public String powerDetail(
            Integer powerId,
            Model model) {

        Power power = heroService.getPowerById(powerId);
        model.addAttribute("power", power);

        return "powers/powerDetail";
    }
}
