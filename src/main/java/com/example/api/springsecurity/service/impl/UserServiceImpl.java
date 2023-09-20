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
 * Nombre de archivo: UserServiceImpl
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.service.impl;

import com.example.api.springsecurity.dto.SaveUser;
import com.example.api.springsecurity.exception.InvalidPasswordException;
import com.example.api.springsecurity.persistence.entity.security.Role;
import com.example.api.springsecurity.persistence.entity.security.User;
import com.example.api.springsecurity.persistence.repository.security.UserRepository;
import com.example.api.springsecurity.service.RoleService;
import com.example.api.springsecurity.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleService roleService;

  @Override
  public User saveCustomer(SaveUser newUser) {

    validatePassword(newUser);

    User user = new User();
    user.setName(newUser.getName());
    user.setUsername(newUser.getUsername());
    Role defaultRole = roleService.findDefaultRole();
    user.setRole(defaultRole);
    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findOneByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  private void validatePassword(SaveUser newUser) {

    if (!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepeatedPassword())) {
      throw new InvalidPasswordException("Passwords do not match");
    }
    if (!newUser.getPassword().equals(newUser.getRepeatedPassword())) {
      throw new InvalidPasswordException("Passwords do not match");
    }

  }
}
