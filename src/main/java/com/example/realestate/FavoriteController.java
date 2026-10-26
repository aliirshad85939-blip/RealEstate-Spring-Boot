package com.example.realestate;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public FavoriteController(
            FavoriteRepository favoriteRepository,
            UserRepository userRepository,
            PropertyRepository propertyRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/favorites")
    public String showFavorites(Principal principal, Model model) {

        User user = getCurrentUser(principal);

        List<Favorite> favorites =
                favoriteRepository.findByUser_Id(user.getId());

        model.addAttribute("favorites", favorites);

        return "favorites";
    }

    @PostMapping("/favorites/add/{propertyId}")
    public String addFavorite(
            @PathVariable Long propertyId,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = getCurrentUser(principal);

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Property not found"));

        boolean alreadyFavorite =
                favoriteRepository.existsByUser_IdAndProperty_Id(
                        user.getId(), propertyId);

        if (!alreadyFavorite) {
            favoriteRepository.save(new Favorite(user, property));
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Property added to favorites.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Property is already in favorites.");
        }

        return "redirect:/favorites";
    }

    @PostMapping("/favorites/remove/{propertyId}")
    public String removeFavorite(
            @PathVariable Long propertyId,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = getCurrentUser(principal);

        favoriteRepository.deleteByUser_IdAndProperty_Id(
                user.getId(), propertyId);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Property removed from favorites.");

        return "redirect:/favorites";
    }

    private User getCurrentUser(Principal principal) {

        if (principal == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Please log in first");
        }

        return userRepository.findByEmailIgnoreCase(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User account not found"));
    }
}