/*
 *                     GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                             Preamble
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
 *
 *   The licenses for most software and other practical works are designed
 * to take away your freedom to share and change the works.  By contrast,
 * the GNU General Public License is intended to guarantee your freedom to
 * share and change all versions of a program--to make sure it remains free
 * software for all its users.  We, the Free Software Foundation, use the
 * GNU General Public License for most of our software; it applies also to
 * any other work released this way by its authors.  You can apply it to
 * your programs, too.
 *
 * Nombre de archivo: SpringDocConfig
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.config.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SpringDocConfig {

  /**
   * Api info.
   *
   * @return the api info
   */
  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI()
            .components(new Components()
                    .addSecuritySchemes("bearer", createAPIKeyScheme()))
            .info(new Info()
                    .title("demo")
                    .description("Java application")
                    .version("V 1.0")
                    .license(
                            new License()
                                    .name("Apache License Version 2.0")
                                    .url("https://www.apache.org/licenses/LICENSE-2.0"))
                    .contact(new Contact()
                            .name("test.com")
                            .url("https://www.test.com/")
                            .email("test@test.com")));
  }


  @Bean
  public GroupedOpenApi allGrup() {
    return GroupedOpenApi.builder()
            .group("all requests")
            .pathsToMatch("/**")
            .build();
  }

  @Bean
  public GroupedOpenApi authGrup() {
    return GroupedOpenApi.builder()
            .group("auth")
            .pathsToMatch("/auth/**")
            .build();
  }

  @Bean
  public GroupedOpenApi categoryGrup() {
    return GroupedOpenApi.builder()
            .group("cateogry")
            .pathsToMatch("/categories/**")

            .build();
  }

  @Bean
  public GroupedOpenApi customerGrup() {
    return GroupedOpenApi.builder()
            .group("customer")
            .pathsToMatch("/customers/**")
            .build();
  }

  @Bean
  public GroupedOpenApi productGrup() {
    return GroupedOpenApi.builder()
            .group("product")
            .pathsToMatch("/products/**")
            .build();
  }

  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer").in(SecurityScheme.In.HEADER).name(HttpHeaders.AUTHORIZATION);
  }
}
