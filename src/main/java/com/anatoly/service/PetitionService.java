package com.anatoly.service;


import org.springframework.stereotype.Service;

import com.anatoly.model.Petition;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetitionService {
    private final List<Petition> petitions = new ArrayList<>();

    public PetitionService() {
        // Add some initial random petitions
        petitions.add(new Petition(1L, "Save the Rainforest", "Join us in protecting our planet's lungs.", List.of("Alice - alice@example.com")));
        petitions.add(new Petition(2L, "Support Local Libraries", "Help keep our libraries funded and open.", List.of("Bob - bob@example.com", "Charlie - charlie@example.com")));
        petitions.add(new Petition(3L, "Promote Renewable Energy", "Encourage clean energy solutions for a sustainable future.", List.of()));
    }


    // Update a petition
    public Petition updatePetition(Long id, String title, String description) {
        Petition petition = getPetitionById(id);
        if (petition != null) {
            petition.setTitle(title);
            petition.setDescription(description);
        }
        return petition;
    }

    // Delete a petition
    public boolean deletePetition(Long id) {
        return petitions.removeIf(p -> p.getId().equals(id));
    }

    public Petition createPetition(String title, String description) {
        Petition petition = new Petition((long) (petitions.size() + 1), title, description, new ArrayList<>());
        petitions.add(petition);
        return petition;
    }

    public Petition getPetitionById(Long id) {
        return petitions.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Petition> getAllPetitions() {
        return petitions;
    }
}
