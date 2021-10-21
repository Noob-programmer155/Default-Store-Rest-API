package com.AmrTm.StoreRestAPI.Configuration;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(Predicate.not(PathSelectors.ant("/actuator/**")))
				.build()
				.apiInfo(info())
				.tags(new Tag("user", "User Rest Operation"),
						new Tag("item", "Item Rest Operation"),
						new Tag("financial", "Financial Rest Operation"))
				.genericModelSubstitutes(ResponseEntity.class,EntityModel.class,List.class);
	}
	
	private ApiInfo info() {
		return new ApiInfoBuilder()
				.title("Store Management Rest API")
				.description("This is sample Rest API in store management basis WebaApp, using \"Admin\" as username and \"Admin\" as password if you want to explore all Rest")
				.version("1.0")
				.license("GNU General Public License v3.0").licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html")
				.contact(new Contact("Arrijal Amar M",null,"AmrrrR572@gmail.com"))
				.build();
	}
}
