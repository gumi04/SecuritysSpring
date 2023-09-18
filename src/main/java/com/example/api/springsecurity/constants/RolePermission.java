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
 * Nombre de archivo: RolePermission
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.constants;

public enum RolePermission {

  READ_ALL_PRODUCTS,
  READ_ONE_PRODUCT,
  CREATE_ONE_PRODUCT,
  UPDATE_ONE_PRODUCT,
  DISABLE_ONE_PRODUCT,

  READ_ALL_CATEGORIES,
  READ_ONE_CATEGORY,
  CREATE_ONE_CATEGORY,
  UPDATE_ONE_CATEGORY,
  DISABLE_ONE_CATEGORY,

  READ_MY_PROFILE;

}
