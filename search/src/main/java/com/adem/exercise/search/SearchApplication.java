package com.adem.exercise.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@SpringBootApplication

@ComponentScan(basePackages={"com.adem.exercise.service", "com.adem.exercise.search", "com.adem.exercise.persistence"})
@EnableJpaRepositories(basePackages={"com.adem.exercise.persistence.repository"})
@EntityScan(basePackages={"com.adem.exercise.persistence.model"})
@EnableSwagger2
@EnableAutoConfiguration
public class SearchApplication {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/document.*"))
				.build();

	}

	@Bean(name = "dictionary")
	public Map<String, Set<String>> dictionary(){
		return new HashMap<>();
	}


	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
	}
}
