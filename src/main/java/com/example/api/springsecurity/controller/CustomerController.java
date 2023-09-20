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
 * Nombre de archivo: CustomerController
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.controller;

import com.example.api.springsecurity.dto.RegistredUser;
import com.example.api.springsecurity.dto.SaveUser;
import com.example.api.springsecurity.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping(value = "/customers")
@Tag(
        name = "Customers",
        description = "Create customers")
public class CustomerController {

  /**
   * The Authentication servicce.
   */
  @Autowired
  private AuthenticationService authenticationServicce;

  /**
   * Register user response entity.
   *
   * @param newUser the new user
   * @return the response entity
   */
  @PreAuthorize("permitAll")
  @Operation(summary = "registrar cleinte")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegistredUser> registerUser(@RequestBody @Valid SaveUser newUser) {
    RegistredUser user = authenticationServicce.registerCustomer(newUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

}
