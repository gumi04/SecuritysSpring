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
 * Nombre de archivo: HttpSecurityConfig
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.config.security;

import com.example.api.springsecurity.config.security.filter.JwtAuthenticationFilter;
import com.example.api.springsecurity.constants.RoleEnum;
import com.example.api.springsecurity.constants.RolePermissionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The type Http security config.
 */
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

  /**
   * The Authentication provider.
   */
  @Autowired
  private AuthenticationProvider authenticationProvider;


  /**
   * The Jwt authentication filter.
   */
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * The Authentication entry point.
   */
  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;
  /**
   * The Access denied handler.
   */
  @Autowired
  private AccessDeniedHandler accessDeniedHandler;
  /**
   * The Authorization manager.
   */
  @Autowired
  private AuthorizationManager<RequestAuthorizationContext> authorizationManager;

  /**
   * Security filter chain security filter chain.
   *
   * @param http the http
   * @return the security filter chain
   * @throws Exception the exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
              .cors(withDefaults())
              .csrf(crsfConfig -> crsfConfig.disable())
              .sessionManagement(sessionMagConfig -> sessionMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authenticationProvider(authenticationProvider)
              .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
              .authorizeHttpRequests(authConfig -> {
                builtRequestMatchersSpringDoc(authConfig);
                authConfig.anyRequest().access(authorizationManager);

              })
              .exceptionHandling(exceptionHandling -> {
                exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
                exceptionHandling.accessDeniedHandler(accessDeniedHandler);
              })
              .build();
  }


  /**
   * Built request matchers.
   *
   * @param authReqConfig the auth req config
   */
  private static void builtRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
    //autorizacion para productos
    authReqConfig.requestMatchers(HttpMethod.GET, "/products")
              .hasAuthority(RolePermissionEnum.READ_ALL_PRODUCTS.name());

    authReqConfig.requestMatchers(HttpMethod.GET, "/products/{id}")
              .hasAuthority(RolePermissionEnum.READ_ONE_PRODUCT.name());

    authReqConfig.requestMatchers(HttpMethod.POST, "/products")
              .hasAuthority(RolePermissionEnum.CREATE_ONE_PRODUCT.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{id}")
              .hasAuthority(RolePermissionEnum.UPDATE_ONE_PRODUCT.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{id}/disabled")
              .hasAuthority(RolePermissionEnum.DISABLE_ONE_PRODUCT.name());


    //autorizacion para categorias

    authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
              .hasAuthority(RolePermissionEnum.READ_ALL_CATEGORIES.name());

    authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{id}")
              .hasAuthority(RolePermissionEnum.READ_ONE_CATEGORY.name());

    authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
              .hasAuthority(RolePermissionEnum.CREATE_ONE_CATEGORY.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{id}")
              .hasAuthority(RolePermissionEnum.UPDATE_ONE_CATEGORY.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{id}/disabled")
              .hasAuthority(RolePermissionEnum.DISABLE_ONE_CATEGORY.name());

    //autorizacion para profile
    authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
              .hasAuthority(RolePermissionEnum.READ_MY_PROFILE.name());

    //autorizacion de endpoint publicos
    authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
    authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
    authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();
    authReqConfig.requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html")
              .permitAll();

    authReqConfig.anyRequest().authenticated();
  }

  /**
   * Built request matchers spring doc.
   *
   * @param authReqConfig the auth req config
   */
  private static void builtRequestMatchersSpringDoc(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
    authReqConfig.requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html")
              .permitAll();

    //authReqConfig.anyRequest().authenticated();
  }

  /**
   * Built request matchers role.
   *
   * @param authReqConfig the auth req config
   */
  private static void builtRequestMatchersRole(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
    //autorizacion para productos
    authReqConfig.requestMatchers(HttpMethod.GET, "/products")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

    authReqConfig.requestMatchers(HttpMethod.GET, "/products/{id}")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

    //validar con un regex
    //authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
    //      .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());


    authReqConfig.requestMatchers(HttpMethod.POST, "/products")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{id}")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{id}/disabled")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name());


    //autorizacion para categorias

    authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

    authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{id}")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

    authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{id}")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

    authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{id}/disabled")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name());

    //autorizacion para profile
    authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
              .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

    //autorizacion de endpoint publicos
    authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
    authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
    authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();
    authReqConfig.requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html")
              .permitAll();

    authReqConfig.anyRequest().authenticated();
  }
}
