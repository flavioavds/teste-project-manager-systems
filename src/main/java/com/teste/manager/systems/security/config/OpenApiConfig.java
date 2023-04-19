package com.teste.manager.systems.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	private static final String API_TITLE = "Teste RESTful API with Java 17 and Spring Boot 3.0.5";
    private static final String API_DESCRIPTION = "Projeto Manager Systems";
    private static final String API_VERSION = "1.0.0";
    private static final String API_LICENSE = "-";
    private static final String API_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";
    private static final String CONTACT_NAME = "Backend Squad";
    private static final String CONTACT_GITLAB = "https://github.com/flavioavds";
    private static final String CONTACT_EMAIL = "favds123@gmail.com";

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(API_TITLE)
						.version(API_VERSION)
						.description(API_DESCRIPTION)
						.termsOfService("http://swagger.io/terms/")
						.license(new License().
								name(API_LICENSE).
								url(API_LICENSE_URL))
						.contact(new Contact()
								.email(CONTACT_EMAIL)
								.name(CONTACT_NAME)
								.url(CONTACT_GITLAB)));
	}
}
