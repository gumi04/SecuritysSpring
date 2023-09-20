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
 * Nombre de archivo: AuthenticationController
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.controller;

import com.example.api.springsecurity.dto.auth.AuthenticationRequest;
import com.example.api.springsecurity.dto.auth.AuthenticationResponse;
import com.example.api.springsecurity.dto.auth.LogoutResponse;
import com.example.api.springsecurity.persistence.entity.security.User;
import com.example.api.springsecurity.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Authentication controller.
 */
@RestController
@RequestMapping("/auth")
@Tag(
        name = "authentication",
        description = "Authentication user")
public class AuthenticationController {

  /**
   * The Authentication service.
   */
  @Autowired
  private AuthenticationService authenticationService;

  /**
   * Auntheticate response entity.
   *
   * @param request the request
   * @return the response entity
   */
  @PreAuthorize("permitAll")
  @Operation(summary = "autenticacion de usarios")
  @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> auntheticate(@RequestBody @Valid
                                                             AuthenticationRequest request) {
    return ResponseEntity.ok(authenticationService.login(request));
  }

  /**
   * Validate response entity.
   *
   * @param jwt the jwt
   * @return the response entity
   */
  @PreAuthorize("permitAll")
  @Operation(summary = "validacion de token")
  @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> validate(@RequestParam String jwt) {

    return ResponseEntity.ok(authenticationService.validateToken(jwt));

  }

  /**
   * Logout response entity.
   *
   * @param request the request
   * @return the response entity
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
    authenticationService.logout(request);
    return ResponseEntity.ok(new LogoutResponse("Logout exitoso"));
  }

  /**
   * Find my profile response entity.
   *
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
  @Operation(summary = "autenticacion de usarios", security = {@SecurityRequirement(name = "bearer")})
  @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> findMyProfile() {
    User user = authenticationService.findLogggedInUser();
    return ResponseEntity.ok(user);
  }
}
