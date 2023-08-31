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
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements ICountryService {

    private final RestTemplate restTemplate;

    public CountryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Country> fetchCountries(String name, Integer population, String sort, Integer limit) {
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
        Predicate<Country> nameFilter = filterCountriesByNamePredicate(name);
        Predicate<Country> populationFilter = filterCountriesByPopulationPredicate(population);
        Comparator<Country> nameComparator = sortCountryByNameComparator(sort);
        countries = countries
                .stream()
                .filter(nameFilter.and(populationFilter))
                .sorted(nameComparator)
                .collect(Collectors.toList());
        if (limit != null) {
            return countries
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        }
        return countries;
    }

    public Predicate<Country> filterCountriesByNamePredicate(String searchString) {
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

    public Predicate<Country> filterCountriesByPopulationPredicate(Integer populationLimit) {
        return country -> {
            if (country == null || country.getPopulation() == null) {
                return false;
            }
            if (populationLimit == null) {
                return true;
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