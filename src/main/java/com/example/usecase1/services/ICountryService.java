package com.example.usecase1.services;

import com.example.usecase1.entities.Country;
import java.util.List;

public interface ICountryService {
    List<Country> fetchCountries(String name, Integer population, String sort, Integer limit);
}