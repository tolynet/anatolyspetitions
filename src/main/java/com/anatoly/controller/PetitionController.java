package com.anatoly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.anatoly.model.Petition;
import com.anatoly.service.PetitionService;

import java.util.List;

@Controller  // Change from @RestController to @Controller
@RequestMapping("/petitions")  // Adjust URL mapping to match your views
public class PetitionController {
    private final PetitionService petitionService;

    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    // Render the form to create a petition
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("petition", new Petition(null, "", "", null));
        return "create-petition";  // Return the view name (Thymeleaf template)
    }

    // Handle the form submission to create a petition
    @PostMapping("/create")
    public String createPetition(@ModelAttribute Petition petition) {
        petitionService.createPetition(petition.getTitle(), petition.getDescription());
        return "redirect:/petitions/view";  // Redirect to the view page after creation
    }

    // Render the page to view/search petitions
    @GetMapping("/view")
    public String viewPetitions(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Petition> petitions;
        if (search != null && !search.isEmpty()) {
            // Filter petitions by title
            petitions = petitionService.getAllPetitions().stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        } else {
            petitions = petitionService.getAllPetitions();
        }
        model.addAttribute("petitions", petitions);
        model.addAttribute("search", search);
        return "view-petitions";  // Return the view name (Thymeleaf template)
    }

    // Search page
    @GetMapping("/search")
    public String searchPage() {
        return "search-petition";
    }

    // Search results page
    @GetMapping("/search-results")
    public String searchResults(@RequestParam String search, Model model) {
        List<Petition> results = petitionService.getAllPetitions().stream()
                .filter(p -> p.getTitle().toLowerCase().contains(search.toLowerCase()))
                .toList();
        model.addAttribute("results", results);
        return "search-results";
    }

    // View petition details
    @GetMapping("/{id}")
    public String viewPetition(@PathVariable Long id, Model model) {
        Petition petition = petitionService.getPetitionById(id);
        if (petition == null) {
            throw new IllegalArgumentException("Petition not found for ID: " + id);
        }
        model.addAttribute("petition", petition);
        return "petition-details"; // Refers to petition-details.html
    }

    // Handle signing the petition
    @PostMapping("/{id}/sign")
    public String signPetition(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            Model model) {
        Petition petition = petitionService.getPetitionById(id);
        if (petition == null) {
            throw new IllegalArgumentException("Petition not found for ID: " + id);
        }
    
        // Add logic to save the signature (you can expand your Petition model to include signatures)
        String signature = String.format("Name: %s, Email: %s", name, email);
        petition.addSignature(signature); // Assume `addSignature` method exists in the Petition class.
    
        model.addAttribute("petition", petition);
        model.addAttribute("successMessage", "Thank you for signing the petition!");
        return "petition-details";
    }
}
