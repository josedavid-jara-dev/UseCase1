package com.example.usecase1.services;

import com.example.usecase1.constants.Constants;
import com.example.usecase1.entities.Country;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
public class CountryServiceImpl implements ICountryService {

    @Override
    public List<Country> fetchCountries() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Country>> response = restTemplate.exchange(
                Constants.COUNTRIES_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<Country> countries = response.getBody();

        if (countries == null) {
            return new ArrayList<>();
        }
        return countries;
    }

    private Predicate<Country> filterCountriesByNamePredicate(String searchString) {
        return country -> {
            if (country == null || country.getName() == null || country.getName().getCommon() == null) {
                return false;
            }
            if (searchString == null) {
                return true;
            }
            return country.getName().getCommon().toLowerCase().contains(searchString.toLowerCase());
        };
    }

    public Predicate<Country> filterCountriesByPopulationPredicate(int populationLimit) {
        return country -> {
            if (country == null || country.getPopulation() == null) {
                return false;
            }
            return country.getPopulation() < (populationLimit * 1000000);  // populationLimit is in millions
        };
    }

    public Comparator<Country> sortCountryByNameComparator(String sortOrder) {
        if ("descend".equalsIgnoreCase(sortOrder)) {
            return (country1, country2) -> country2.getName().getCommon().compareTo(country1.getName().getCommon());
        }
        // Default to ascending sort
        return Comparator.comparing(country -> country.getName().getCommon());
    }
}