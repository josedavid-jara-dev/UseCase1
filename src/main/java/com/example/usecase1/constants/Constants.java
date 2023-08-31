package com.example.usecase1.constants;

import com.example.usecase1.exceptions.ApiException;

public class Constants {

    private Constants() throws ApiException {
        throw new ApiException("This is a constants class and cant be instantiated.");
    }

    public static final String COUNTRIES_API_URL = "https://restcountries.com/v3.1/all";
}
