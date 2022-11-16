package ca.lichangzhang.SuperheroSighting.controller;

import ca.lichangzhang.SuperheroSighting.dto.Hero;
import ca.lichangzhang.SuperheroSighting.dto.Organization;
import ca.lichangzhang.SuperheroSighting.dto.Power;
import ca.lichangzhang.SuperheroSighting.service.HeroService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Controller
public class OrganizationController {

    @Autowired
    HeroService heroService;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {

        List<Organization> organizations = heroService.getAllOrganizations();

        model.addAttribute("organizations", organizations);

        return "organizations";
    }

    @GetMapping("organizations/addOrganization")
    public String displayAddOrganizations(Model model) {

        model.addAttribute("organization", new Organization());

        return "organizations/addOrganization";
    }

    @PostMapping("organizations/addOrganization")
    public String addOrganization(
            @Valid Organization organization,
            BindingResult result,
            HttpServletRequest request,
            Model model) {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String phone = request.getParameter("phone");

        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setPhone(phone);

        model.addAttribute("organizatioin", organization);

        if (result.hasErrors()) {
            return "organizations/addOrganization";
        }

        heroService.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer organizationId) {
        
        heroService.deleteOrganizationById(organizationId);
        return "redirect:/organizations";
    }

    @GetMapping("organizations/editOrganization")
    public String editStudent(Integer organizationId, Model model) {
        
        Organization organization = heroService.getOrganizationById(organizationId);
        model.addAttribute("organization", organization);
        
        return "organizations/editOrganization";
    }

    @PostMapping("organizations/editOrganization")
    public String performEditOrganization(
            @Valid Organization organization, 
            BindingResult result, 
            Model model) {
        
         if (result.hasErrors()) {
            return "organizations/editOrganization";
        }
         
        heroService.updateOrganization(organization);        
        return "redirect:/organizations";
    }

    @GetMapping("organizations/organizationDetail")
    public String organizationDetail(Integer organizationId, Model model) {

        Organization organization = heroService.getOrganizationById(organizationId); 
        model.addAttribute("organization", organization);
        
        return "organizations/organizationDetail";
    }
}
