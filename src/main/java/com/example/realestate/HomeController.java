package com.example.realestate;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final PropertyRepository propertyRepository;
    private final EnquiryRepository enquiryRepository;

    public HomeController(
            PropertyRepository propertyRepository,
            EnquiryRepository enquiryRepository) {
        this.propertyRepository = propertyRepository;
        this.enquiryRepository = enquiryRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        return "home";
    }

    @GetMapping("/properties")
    public String searchProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        // Normalize empty search fields.
        if (location != null && location.isBlank()) {
            location = null;
        }

        if (location != null) {
            location = location.trim();
        }

        if (type != null && type.isBlank()) {
            type = null;
        }

        if (type != null) {
            type = type.trim();
        }

        // Validate the price range.
        if (minPrice != null && minPrice < 0
                || maxPrice != null && maxPrice < 0) {
            model.addAttribute("properties", List.<Property>of());
            model.addAttribute("errorMessage",
                    "Prices cannot be negative.");
        } else if (minPrice != null && maxPrice != null
                && minPrice > maxPrice) {
            model.addAttribute("properties", List.<Property>of());
            model.addAttribute("errorMessage",
                    "Minimum price cannot exceed maximum price.");
        } else {
            List<Property> properties =
                    propertyRepository.searchProperties(
                            location, type, minPrice, maxPrice);

            model.addAttribute("properties", properties);
        }

        model.addAttribute("location", location);
        model.addAttribute("type", type);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "properties";
    }

    @GetMapping("/property/{id}")
    public String propertyDetails(
            @PathVariable Long id, Model model) {

        Property property = propertyRepository.findById(id)
                .orElse(null);

        if (property == null) {
            return "redirect:/";
        }

        model.addAttribute("property", property);
        return "property-details";
    }

    // Open Contact Owner form
    @GetMapping("/property/{id}/enquiry")
    public String showEnquiryForm(
            @PathVariable Long id, Model model) {

        Property property = propertyRepository.findById(id)
                .orElse(null);

        if (property == null) {
            return "redirect:/";
        }

        model.addAttribute("property", property);
        model.addAttribute("enquiry", new Enquiry());

        return "contact-form";
    }

    // Submit enquiry
    @PostMapping("/property/{id}/enquiry")
    public String submitEnquiry(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String message,
            RedirectAttributes redirectAttributes) {

        Property property = propertyRepository.findById(id)
                .orElse(null);

        if (property == null) {
            return "redirect:/";
        }

        Enquiry enquiry = new Enquiry();
        enquiry.setPropertyId(id);
        enquiry.setName(name.trim());
        enquiry.setEmail(email.trim());
        enquiry.setPhone(phone.trim());
        enquiry.setMessage(message.trim());

        enquiryRepository.save(enquiry);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Your enquiry has been submitted successfully!");

        return "redirect:/property/" + id;
    }
}