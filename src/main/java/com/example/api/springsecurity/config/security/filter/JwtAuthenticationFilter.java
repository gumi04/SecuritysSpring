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
 * Nombre de archivo: JwtAuthenticationFilter
 * Autor: 319207
 * Fecha de creación: septiembre 18, 2023
 */

package com.example.api.springsecurity.config.security.filter;

import com.example.api.springsecurity.exception.ObjectNotFoundException;
import com.example.api.springsecurity.persistence.entity.security.JwtToken;
import com.example.api.springsecurity.persistence.repository.security.JwtTokenRepository;
import com.example.api.springsecurity.service.UserService;
import com.example.api.springsecurity.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The type Jwt authentication filter.
 */
//@Component
public class JwtAuthenticationFilter  {

  /**
   * The Jwt service.
   */
  @Autowired
  private JwtService jwtService;
  /**
   * The User service.
   */
  @Autowired
  private UserService userService;
  /**
   * The Jwt token repository.
   */
  @Autowired
  private JwtTokenRepository jwtTokenRepository;

  /**
   * Do filter internal.
   *
   * @param request     the request
   * @param response    the response
   * @param filterChain the filter chain
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */

  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String jwt = jwtService.extractJwtFromRequest(request);

    //1.- Obtener el authorization header
    //2.- Obtener token

    if (jwt == null || !StringUtils.hasText(jwt)) {
      filterChain.doFilter(request, response);
      return;

    }

    //2.1 Obtener token no expeirado desde BD y valido
    Optional<JwtToken> token = jwtTokenRepository.findByToken(jwt);
    boolean isValid = validateToken(token);

    if (!isValid) {
      filterChain.doFilter(request, response);
      return;
    }

    //3.- Obterner el subjhect/username desde el token, valida el formato del toke y fecha de expiracion
    String username = jwtService.extartUername(jwt);

    //4.- Setear el objeto authentication dentro de security context holder
    UserDetails userDetails = userService.findOneByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username, null, userDetails.getAuthorities()
    );
    authToken.setDetails(new WebAuthenticationDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);

    //5.- ejecutar el resto de filtros
    filterChain.doFilter(request, response);

  }

  /**
   * Validate token boolean.
   *
   * @param optionalJwtToken the optional jwt token
   * @return the boolean
   */
  private boolean validateToken(Optional<JwtToken> optionalJwtToken) {
    if (!optionalJwtToken.isPresent()) {
      return false;
    }

    JwtToken token = optionalJwtToken.get();
    Date now = new Date(System.currentTimeMillis());
    boolean isValid = token.isValid() && token.getExpiration().after(now);

    if (!isValid) {
      updateTokenStatus(token);
    }
    return isValid;
  }

  /**
   * Update token status.
   *
   * @param token the token
   */
  private void updateTokenStatus(JwtToken token) {
    token.setValid(false);
    jwtTokenRepository.save(token);
  }
}
