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
 * Nombre de archivo: OperationRepository
 * Autor: 319207
 * Fecha de creación: septiembre 19, 2023
 */

package com.example.api.springsecurity.persistence.repository.security;

import com.example.api.springsecurity.persistence.entity.security.Operation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Operation repository.
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
  /**
   * Find by public access list.
   *
   * @return the list
   */
  @Query("SELECT o FROM Operation o WHERE o.permitAll = true")
  List<Operation> findByPublicAccess();

  /**
   * Find by name optional.
   *
   * @param defaultRole the default role
   * @return the optional
   */
  Optional<Operation> findByName(String defaultRole);
}
