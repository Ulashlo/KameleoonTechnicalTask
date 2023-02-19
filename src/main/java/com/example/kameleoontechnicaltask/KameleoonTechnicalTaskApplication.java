package com.example.kameleoontechnicaltask;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Kameleoon technical task API", version = "1.0"))
@SecurityScheme(name = "basic", scheme = "basic", type = SecuritySchemeType.HTTP)
public class KameleoonTechnicalTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(KameleoonTechnicalTaskApplication.class, args);
    }

}
