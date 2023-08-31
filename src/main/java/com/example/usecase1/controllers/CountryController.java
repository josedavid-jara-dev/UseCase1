package com.example.usecase1.controllers;

import com.example.usecase1.entities.Country;
import com.example.usecase1.services.ICountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
public class CountryController {

    private final ICountryService countryService;

    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public List<Country> getCountries(
            @RequestParam(required = false) Optional<String> param1,
            @RequestParam(required = false) Optional<String> param2,
            @RequestParam(required = false) Optional<String> param3,
            @RequestParam(required = false) Optional<String> param4) {

        // Do something with parameters param1, param2, param3, param4 if needed
        return countryService.fetchCountries();
    }
}
