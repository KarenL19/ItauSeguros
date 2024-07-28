package com.store.itauseguros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ItauSegurosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItauSegurosApplication.class, args);
    }

}
