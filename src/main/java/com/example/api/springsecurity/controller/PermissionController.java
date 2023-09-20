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
 * Nombre de archivo: PermissionController
 * Autor: 319207
 * Fecha de creación: septiembre 19, 2023
 */

package com.example.api.springsecurity.controller;

import com.example.api.springsecurity.dto.SavePermission;
import com.example.api.springsecurity.dto.ShowPermission;
import com.example.api.springsecurity.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
@Tag(
        name = "Permissions",
        description = "Crud of permissions")
public class PermissionController {

  @Autowired
  private PermissionService permissionService;

  @Operation(summary = "obtener permisos paginados", security = {@SecurityRequirement(name = "bearer")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<ShowPermission>> findAll(Pageable pageable) {

    Page<ShowPermission> permissions = permissionService.findAll(pageable);

    if (permissions.hasContent()) {
      return ResponseEntity.ok(permissions);
    }

    return ResponseEntity.notFound().build();
  }

  @Operation(summary = "obtiene un permiso por su id", security = {@SecurityRequirement(name = "bearer")})
  @GetMapping(value = "/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ShowPermission> findOneById(@PathVariable Long permissionId) {
    Optional<ShowPermission> permission = permissionService.findOneById(permissionId);

    if (permission.isPresent()) {
      return ResponseEntity.ok(permission.get());
    }

    return ResponseEntity.notFound().build();
  }

  @Operation(summary = "crea un nuevo permiso", security = {@SecurityRequirement(name = "bearer")})
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ShowPermission> createOne(@RequestBody @Valid SavePermission savePermission) {
    ShowPermission permission = permissionService.createOne(savePermission);
    return ResponseEntity.status(HttpStatus.CREATED).body(permission);
  }

  @Operation(summary = "elimina un permiso por su id", security = {@SecurityRequirement(name = "bearer")})
  @DeleteMapping(value = "/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ShowPermission> deleteOneById(@PathVariable Long permissionId) {
    ShowPermission permission = permissionService.deleteOneById(permissionId);
    return ResponseEntity.ok(permission);
  }
}
