package com.example.realestate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PropertyRepository propertyRepository;

    public AdminController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        model.addAttribute("totalProperties", propertyRepository.count());
        return "admin";
    }

    @GetMapping("/properties/new")
    public String showAddForm(Model model) {
        model.addAttribute("property", new Property());
        model.addAttribute("formTitle", "Add Property");
        return "admin-property-form";
    }

    @PostMapping("/properties/save")
    public String saveProperty(
            @ModelAttribute Property property,
            RedirectAttributes redirectAttributes) {

        propertyRepository.save(property);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Property saved successfully!");
        return "redirect:/admin";
    }

    @GetMapping("/properties/edit/{id}")
    public String showEditForm(
            @PathVariable Long id, Model model) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid property ID: " + id));

        model.addAttribute("property", property);
        model.addAttribute("formTitle", "Edit Property");
        return "admin-property-form";
    }

    @PostMapping("/properties/delete/{id}")
    public String deleteProperty(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        if (!propertyRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Property not found.");
            return "redirect:/admin";
        }

        propertyRepository.deleteById(id);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Property deleted successfully!");
        return "redirect:/admin";
    }
}