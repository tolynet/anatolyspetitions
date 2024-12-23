package com.anatoly.model;

import java.util.ArrayList;
import java.util.List;

public class Petition {
    private Long id;
    private String title;
    private String description;
    private List<String> signatures = new ArrayList<>();

    public Petition(Long id, String title, String description, List<String> signatures) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.signatures = signatures != null ? new ArrayList<>(signatures) : new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addSignature(String signature) {
        this.signatures.add(signature);
    }

    public List<String> getSignatures() {
        return signatures;
    }
}
