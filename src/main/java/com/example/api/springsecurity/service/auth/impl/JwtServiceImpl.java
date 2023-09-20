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
 * Nombre de archivo: JwtServiceImpl
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.service.auth.impl;

import com.example.api.springsecurity.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * The type Jwt service.
 */
@Service
public class JwtServiceImpl implements JwtService {

  /**
   * The Expiration in minutes.
   */
  @Value("${security.jwt.expiration-in-minutes}")
  public Long EXPIRATION_IN_MINUTES;

  /**
   * The Secret key.
   */
  @Value("${security.jwt.secret-key}")
  public String SECRET_KEY;

  /**
   * Generate token string.
   *
   * @param user   the user
   * @param claims the claims
   * @return the string
   */
  @Override
  public String generateToken(UserDetails user, Map<String, Object> claims) {

    Date issuedAt = new Date(System.currentTimeMillis());
    Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getUsername())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .signWith(generateKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * Extart uername string.
   *
   * @param jwt the jwt
   * @return the string
   */
  @Override
  public String extartUername(String jwt) {
    return extactAllClaims(jwt).getSubject();
  }

  /**
   * Extract jwt from request string.
   *
   * @param request the request
   * @return the string
   */
  @Override
  public String extractJwtFromRequest(HttpServletRequest request) {

    String authorizationHeader = request.getHeader("Authorization");
    if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
      return null;
    }

    return authorizationHeader.split(" ")[1];
  }

  /**
   * Extract expiration date.
   *
   * @param jwt the jwt
   * @return the date
   */
  @Override
  public Date extractExpiration(String jwt) {
    return extactAllClaims(jwt).getExpiration();
  }

  /**
   * Extact all claims claims.
   *
   * @param jwt the jwt
   * @return the claims
   */
  private Claims extactAllClaims(String jwt) {
    return Jwts.parserBuilder()
            .setSigningKey(generateKey())
            .build()
            .parseClaimsJws(jwt).getBody();
  }

  /**
   * Generate key key.
   *
   * @return the key
   */
  private Key generateKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
  }
}
