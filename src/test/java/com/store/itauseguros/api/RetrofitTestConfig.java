package com.store.itauseguros.api;

import com.store.itauseguros.api.client.CucumberClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitTestConfig {

    private static final String BASE_URL = "http://localhost:8080/system-insurance/";

    @Bean
    public CucumberClient cucumberClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CucumberClient.class);
    }
}