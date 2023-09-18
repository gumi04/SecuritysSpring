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
import com.example.api.springsecurity.service.UserService;
import com.example.api.springsecurity.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    //1.- Obtener el encabezado de authorization
    String authorizationHeader = request.getHeader("Authorization");
    if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    //2.- Obtener el token jwt desde el encabezado
    String jwt = authorizationHeader.split(" ")[1];

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
}
