package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.service.HeroService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
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
    public String editPower(Integer powerId, Model model) {

        Power power = heroService.getPowerById(powerId);
        model.addAttribute("power", power);
 
        return "powers/editPower";
    }

    @PostMapping("powers/editPower")
    public String performEditPower(@Valid Power power, BindingResult result, Model model) {

          if (result.hasErrors()) {
            return "powers/editPower";
        }
        
        heroService.updatePower(power);
        return "redirect:/powers";
    }

    @GetMapping("powers/powerDetail")
    public String powerDetail(Integer powerId, Model model) {

        Power power = heroService.getPowerById(powerId);
        model.addAttribute("power", power);

        return "powers/powerDetail";
    }
}
