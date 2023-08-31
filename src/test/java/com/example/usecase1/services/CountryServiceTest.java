package com.example.usecase1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.example.usecase1.entities.Country;
import com.example.usecase1.entities.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    @Mock
    private RestTemplate restTemplate;

    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl(restTemplate);
    }

    @Test
    void testFetchCountriesFullList() {
        // Setup
        List<Country> mockCountries = createMockCountries();
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Object>>any()))
                .thenReturn(ResponseEntity.ok(mockCountries));
        // Execute
        List<Country> countries = countryService.fetchCountries(null, null, null, null);

        // Assert
        assertEquals(4, countries.size());
    }

    @Test
    void testFetchCountriesByName() {
        // Setup
        List<Country> mockCountries = createMockCountries();
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Object>>any()))
                .thenReturn(ResponseEntity.ok(mockCountries));

        // Execute
        List<Country> countries = countryService.fetchCountries("er", null, null, null);

        // Assert
        assertEquals(3, countries.size());
        assertTrue(countries.stream().allMatch(country -> country.getName().getCommon().contains("er")));
    }

    @Test
    void testFetchCountriesByPopulation() {
        // Setup
        List<Country> mockCountries = createMockCountries();
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Object>>any()))
                .thenReturn(ResponseEntity.ok(mockCountries));

        // Execute
        List<Country> countries = countryService.fetchCountries(null, 15, null, null);

        // Assert
        assertEquals(3, countries.size());
        assertTrue(countries.stream().allMatch(country -> country.getPopulation() < 15 * 1000000));
    }

    @Test
    void testFetchCountriesSorted() {
        // Setup
        List<Country> mockCountries = createMockCountries();
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Object>>any()))
                .thenReturn(ResponseEntity.ok(mockCountries));

        // Execute
        List<Country> countries = countryService.fetchCountries(null, null, "ascend", null);

        // Assert
        List<String> countryNames = countries.stream()
                .map(country -> country.getName().getCommon())
                .collect(Collectors.toList());

        assertEquals(Arrays.asList("Algeria", "Germany", "Nigeria", "Zimbabwe"), countryNames);
    }

    @Test
    void testFetchCountriesWithLimit() {
        // Setup
        List<Country> mockCountries = createMockCountries();
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Object>>any()))
                .thenReturn(ResponseEntity.ok(mockCountries));

        // Execute
        List<Country> countries = countryService.fetchCountries(null, null, null, 2);

        // Assert
        assertEquals(2, countries.size());
    }

    @Test
    void testFetchCountriesWithNullValues() {
        // Setup
        List<Country> mockCountries = createMockCountries();
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Object>>any()))
                .thenReturn(ResponseEntity.ok(mockCountries));

        // Execute
        List<Country> countries = countryService.fetchCountries(null, null, null, null);

        // Assert
        assertEquals(4, countries.size());
    }

    @Test
    void testFetchCountriesEmptyListFromAPI() {
        // Setup
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Object>>any()))
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));
        // Execute
        List<Country> countries = countryService.fetchCountries(null, null, null, null);

        // Assert
        assertTrue(countries.isEmpty());
    }

    private List<Country> createMockCountries() {
        return Arrays.asList(
                new Country(new Name("Algeria", "People's Democratic Republic of Algeria"), 10 * 1000000),
                new Country(new Name("Germany", "Federal Republic of Germany"), 20 * 1000000),
                new Country(new Name("Nigeria", "Federal Republic of Nigeria"), 12 * 1000000),
                new Country(new Name("Zimbabwe", "Republic of Zimbabwe"), 14 * 1000000)
        );
    }
}
