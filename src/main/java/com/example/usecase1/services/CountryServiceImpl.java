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
}