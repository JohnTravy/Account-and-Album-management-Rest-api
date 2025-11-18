package com.travy.SpringRestAPI.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "TOMIWA API",
        version = "Versions 1.0",
        contact = @Contact(
            name = "Tomiwa", email = "jttomiwa@gmail.com",
            url = "http://tomiwa.com"
        ),

        license = @License(
            name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
        ),

        termsOfService = "https://tomiwa.com/TOS",

        description = "Spring Boot Restful Api Demo by Tomiwa"
    )
)

public class SwaggerConfig {
    
}
