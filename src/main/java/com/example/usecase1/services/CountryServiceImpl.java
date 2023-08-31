package com.example.usecase1.services;

import com.example.usecase1.constants.Constants;
import com.example.usecase1.entities.Country;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private List<Country> filterCountriesByName(List<Country> countries, Optional<String> maybeName) {
        if (countries == null || countries.isEmpty()) {
            return countries;
        }

        // Filter
        return countries.stream()
                .filter(country -> {
                    if (country == null || country.getName() == null || country.getName().getCommon() == null) {
                        return false;
                    }

                    return maybeName.map(str -> country.getName().getCommon().toLowerCase().contains(str.toLowerCase()))
                            .orElse(true);
                })
                .collect(Collectors.toList());
    }
}