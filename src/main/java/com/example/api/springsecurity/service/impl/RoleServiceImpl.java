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
 * Nombre de archivo: RoleServieImpl
 * Autor: 319207
 * Fecha de creación: septiembre 19, 2023
 */

package com.example.api.springsecurity.service.impl;

import com.example.api.springsecurity.exception.ObjectNotFoundException;
import com.example.api.springsecurity.persistence.entity.security.Role;
import com.example.api.springsecurity.persistence.repository.security.RoleRepository;
import com.example.api.springsecurity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Role service.
 */
@Service
public class RoleServiceImpl implements RoleService {

  /**
   * The Default role.
   */
  @Value("${security.default.role}")
  private String defaultRole;

  /**
   * The Role repository.
   */
  @Autowired
  private RoleRepository roleRepository;

  /**
   * Find default role role.
   *
   * @return the role
   */
  @Override
  public Role findDefaultRole() {
    return roleRepository.findByName(defaultRole)
            .orElseThrow(() -> new ObjectNotFoundException("Role not found. Default role"));
  }
}
