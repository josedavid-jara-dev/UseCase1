package com.example.usecase1.entities;

public class Country {
    private Name name;
    private Integer population;

    public Country() {
    }

    public Country(Name name, Integer population) {
        this.name = name;
        this.population = population;
    }

    // Add other fields as needed

    // Getters and Setters
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
