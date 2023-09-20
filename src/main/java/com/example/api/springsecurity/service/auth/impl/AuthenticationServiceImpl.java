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
import com.example.api.springsecurity.persistence.entity.security.JwtToken;
import com.example.api.springsecurity.persistence.entity.security.User;
import com.example.api.springsecurity.persistence.repository.security.JwtTokenRepository;
import com.example.api.springsecurity.service.UserService;
import com.example.api.springsecurity.service.auth.AuthenticationService;
import com.example.api.springsecurity.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * The type Authentication service.
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  /**
   * The User service.
   */
  @Autowired
  private UserService userService;
  /**
   * The Jwt service.
   */
  @Autowired
  private JwtService jwtService;
  /**
   * The Authentication manager.
   */
  @Autowired
  private AuthenticationManager authenticationManager;
  /**
   * The Jwt token repository.
   */
  @Autowired
  private JwtTokenRepository jwtTokenRepository;

  /**
   * Register customer registred user.
   *
   * @param newUser the new user
   * @return the registred user
   */
  @Override
  public RegistredUser registerCustomer(SaveUser newUser) {
    User user = userService.saveCustomer(newUser);
    String jwt = jwtService.generateToken(user, generateExtraClaims(user));
    saveUserToken(user, jwt);
    RegistredUser userDto = new RegistredUser();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setUserName(user.getName());
    userDto.setRole(user.getRole().getName());

    userDto.setJwt(jwt);


    return userDto;
  }


  /**
   * Login authentication response.
   *
   * @param request the request
   * @return the authentication response
   */
  @Override
  public AuthenticationResponse login(AuthenticationRequest request) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword());
    authenticationManager.authenticate(authentication);

    User user = userService.findOneByUsername(request.getUsername())
            .orElseThrow(() -> new ObjectNotFoundException("User not found"));
    String jwt = jwtService.generateToken(user, generateExtraClaims(user));
    saveUserToken(user, jwt);

    AuthenticationResponse authResp = new AuthenticationResponse();

    authResp.setJwt(jwt);
    return authResp;
  }

  /**
   * Validate token boolean.
   *
   * @param jwt the jwt
   * @return the boolean
   */
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

  /**
   * Find loggged in user user.
   *
   * @return the user
   */
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

  /**
   * Logout.
   *
   * @param request the request
   */
  @Override
  public void logout(HttpServletRequest request) {

    String jwt = jwtService.extractJwtFromRequest(request);
    if (jwt == null || !StringUtils.hasText(jwt)) {
      return;
    }

    Optional<JwtToken> token = jwtTokenRepository.findByToken(jwt);

    if (token.isPresent() && token.get().isValid()) {
      token.get().setValid(false);
      jwtTokenRepository.save(token.get());
    }

  }

  /**
   * Generate extra claims map.
   *
   * @param user the user
   * @return the map
   */
  private Map<String, Object> generateExtraClaims(User user) {
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("name", user.getName());
    extraClaims.put("role", user.getRole().getName());
    extraClaims.put("authorities", user.getAuthorities());
    return extraClaims;
  }

  /**
   * Save user token.
   *
   * @param user the user
   * @param jwt  the jwt
   */
  private void saveUserToken(User user, String jwt) {
    JwtToken token = new JwtToken();
    token.setToken(jwt);
    token.setUser(user);
    token.setExpiration(jwtService.extractExpiration(jwt));
    token.setValid(true);
    jwtTokenRepository.save(token);

  }

}
