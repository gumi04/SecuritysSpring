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
 * Nombre de archivo: AuthenticationServiceImpl
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.service.auth.impl;

import com.example.api.springsecurity.dto.RegistredUser;
import com.example.api.springsecurity.dto.SaveUser;
import com.example.api.springsecurity.dto.auth.AuthenticationRequest;
import com.example.api.springsecurity.dto.auth.AuthenticationResponse;
import com.example.api.springsecurity.exception.ObjectNotFoundException;
import com.example.api.springsecurity.persistence.entity.security.User;
import com.example.api.springsecurity.service.UserService;
import com.example.api.springsecurity.service.auth.AuthenticationService;
import com.example.api.springsecurity.service.auth.JwtService;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private UserService userService;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public RegistredUser registerCustomer(SaveUser newUser) {
    User user = userService.saveCustomer(newUser);

    RegistredUser userDto = new RegistredUser();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setUserName(user.getName());
    userDto.setRole(user.getRole().getName());

    userDto.setJwt(jwtService.generateToken(user, generateExtraClaims(user)));
    return userDto;
  }

  @Override
  public AuthenticationResponse login(AuthenticationRequest request) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword());
    authenticationManager.authenticate(authentication);

    User user = userService.findOneByUsername(request.getUsername())
            .orElseThrow(() -> new ObjectNotFoundException("User not found"));


    AuthenticationResponse authResp = new AuthenticationResponse();
    authResp.setJwt(jwtService.generateToken(user, generateExtraClaims(user)));
    return authResp;
  }

  @Override
  public Boolean validateToken(String jwt) {
    try {
      jwtService.extartUername(jwt);
      return true;
    } catch (Exception e) {
      log.info("token not valid for jwt " + jwt);
      log.info("error: " + e.getMessage());
      return false;
    }

  }

  @Override
  public User findLogggedInUser() {
    /*
    if (auth instanceof UsernamePasswordAuthenticationToken authToken){
      String username = (String) authToken.getPrincipal();
      return userService.findOneByUsername(username)
              .orElseThrow(() -> new ObjectNotFoundException("User not found: Username " + username));
    }
    return null;*/
    UsernamePasswordAuthenticationToken auth =
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

    String username = (String) auth.getPrincipal();
    return userService.findOneByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User not found: Username " + username));

  }

  private Map<String, Object> generateExtraClaims(User user) {
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("name", user.getName());
    extraClaims.put("role", user.getRole().getName());
    extraClaims.put("authorities", user.getAuthorities());
    return extraClaims;
  }
}
