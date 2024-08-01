package com.store.itauseguros.api;

import com.store.itauseguros.ItauSegurosApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = ItauSegurosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberOptions(
        features = "src/test/java/com/store/itauseguros/resources/features",
        glue = "com.store.itauseguros.api",
        plugin = {"pretty","json:target/cucumber-reports/Cucumber.json"}
)
public class RunCucumberTest {
}