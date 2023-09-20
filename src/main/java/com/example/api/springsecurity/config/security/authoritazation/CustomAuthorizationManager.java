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
 * Nombre de archivo: CustomAuthorizationManager
 * Autor: 319207
 * Fecha de creación: septiembre 19, 2023
 */

package com.example.api.springsecurity.config.security.authoritazation;

import com.example.api.springsecurity.exception.ObjectNotFoundException;
import com.example.api.springsecurity.persistence.entity.security.GrantedPermission;
import com.example.api.springsecurity.persistence.entity.security.Operation;
import com.example.api.springsecurity.persistence.entity.security.User;
import com.example.api.springsecurity.persistence.repository.security.OperationRepository;
import com.example.api.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

/**
 * The type Custom authorization manager.
 */
@Component
@Log4j2
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

  /**
   * The Operation repository.
   */
  @Autowired
  private OperationRepository operationRepository;
  /**
   * The User service.
   */
  @Autowired
  private UserService userService;


  /**
   * Check authorization decision.
   *
   * @param authentication the authentication
   * @param requestContext the request context
   * @return the authorization decision
   */
  @Override
  public AuthorizationDecision check(Supplier<Authentication> authentication,
                                     RequestAuthorizationContext requestContext) {
    HttpServletRequest request = requestContext.getRequest();

    String url = extractUrl(request);
    String method = request.getMethod();

    boolean isPublic = isPublic(url, method);

    if (isPublic) {
      return new AuthorizationDecision(true);
    }

    boolean isGranted = isGranted(url, method, authentication.get());


    return new AuthorizationDecision(isGranted);
  }

  /**
   * Is granted boolean.
   *
   * @param url            the url
   * @param method         the method
   * @param authentication the authentication
   * @return the boolean
   */
  private boolean isGranted(String url, String method, Authentication authentication) {

    if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
      throw new AuthenticationCredentialsNotFoundException("User not logged in");
    }

    List<Operation> operationList = obtainedOperations(authentication);
    return getOperationPredicate(url, method, operationList);

  }


  /**
   * Obtained operations list.
   *
   * @param authentication the authentication
   * @return the list
   */
  private List<Operation> obtainedOperations(Authentication authentication) {
    UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
    String username = (String) authToken.getPrincipal();
    User user = userService.findOneByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User not found: " + username));

    return user.getRole().getPermissions().stream()
            .map(GrantedPermission::getOperation)
            .collect(Collectors.toList());
  }

  /**
   * Is public boolean.
   *
   * @param url    the url
   * @param method the method
   * @return the boolean
   */
  private boolean isPublic(String url, String method) {
    List<Operation> publicAccessEndpoints = operationRepository.findByPublicAccess();
    return getOperationPredicate(url, method, publicAccessEndpoints);

  }

  /**
   * Extract url string.
   *
   * @param request the request
   * @return the string
   */
  private String extractUrl(HttpServletRequest request) {
    String contextPath = request.getContextPath();
    String url = request.getRequestURI();
    url = url.replace(contextPath, "");
    log.info(url);
    return url;

  }


  /**
   * Gets operation predicate.
   *
   * @param url           the url
   * @param method        the method
   * @param operationList the operation list
   * @return the operation predicate
   */
  private boolean getOperationPredicate(String url, String method, List<Operation> operationList) {
    return operationList.stream().anyMatch(item -> {
      String basePath = item.getModule().getBasePath();
      Pattern pattern = Pattern.compile(basePath.concat(item.getPath()));
      Matcher matcher = pattern.matcher(url);
      return matcher.matches() && item.getHttpMethod().equals(method);
    });
  }
}
