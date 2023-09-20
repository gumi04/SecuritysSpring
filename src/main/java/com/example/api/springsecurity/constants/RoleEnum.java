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
 * Nombre de archivo: Role
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.constants;

import java.util.Arrays;
import java.util.List;

public enum RoleEnum {
  ADMINISTRATOR(Arrays.asList(
          RolePermissionEnum.READ_ALL_PRODUCTS,
          RolePermissionEnum.READ_ONE_PRODUCT,
          RolePermissionEnum.CREATE_ONE_PRODUCT,
          RolePermissionEnum.UPDATE_ONE_PRODUCT,
          RolePermissionEnum.DISABLE_ONE_PRODUCT,
          RolePermissionEnum.READ_ALL_CATEGORIES,
          RolePermissionEnum.READ_ONE_CATEGORY,
          RolePermissionEnum.CREATE_ONE_CATEGORY,
          RolePermissionEnum.UPDATE_ONE_CATEGORY,
          RolePermissionEnum.DISABLE_ONE_CATEGORY,
          RolePermissionEnum.READ_MY_PROFILE
  )),
  ASSISTANT_ADMINISTRATOR(Arrays.asList(
          RolePermissionEnum.READ_ALL_PRODUCTS,
          RolePermissionEnum.READ_ONE_PRODUCT,
          RolePermissionEnum.UPDATE_ONE_PRODUCT,

          RolePermissionEnum.READ_ALL_CATEGORIES,
          RolePermissionEnum.READ_ONE_CATEGORY,
          RolePermissionEnum.UPDATE_ONE_CATEGORY,

          RolePermissionEnum.READ_MY_PROFILE
  )),
  CUSTOMER(Arrays.asList(
          RolePermissionEnum.READ_MY_PROFILE
  ));

  private List<RolePermissionEnum> permissions;

  RoleEnum(List<RolePermissionEnum> permissions) {
    this.permissions = permissions;
  }

  public List<RolePermissionEnum> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<RolePermissionEnum> permissions) {
    this.permissions = permissions;
  }
}
