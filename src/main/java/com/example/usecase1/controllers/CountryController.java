package com.example.usecase1.controllers;

import com.example.usecase1.entities.Country;
import com.example.usecase1.services.ICountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CountryController {

    private final ICountryService countryService;

    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public List<Country> getCountries(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer population,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit) {
        return countryService.fetchCountries(name, population, sort, limit);
    }
}
