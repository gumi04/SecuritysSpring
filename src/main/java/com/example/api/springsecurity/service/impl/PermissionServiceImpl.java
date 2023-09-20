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
 * Nombre de archivo: PermissionServiceImpl
 * Autor: 319207
 * Fecha de creación: septiembre 19, 2023
 */

package com.example.api.springsecurity.service.impl;

import com.example.api.springsecurity.dto.SavePermission;
import com.example.api.springsecurity.dto.ShowPermission;
import com.example.api.springsecurity.exception.ObjectNotFoundException;
import com.example.api.springsecurity.persistence.entity.security.GrantedPermission;
import com.example.api.springsecurity.persistence.entity.security.Operation;
import com.example.api.springsecurity.persistence.entity.security.Role;
import com.example.api.springsecurity.persistence.repository.security.OperationRepository;
import com.example.api.springsecurity.persistence.repository.security.PermissionRepository;
import com.example.api.springsecurity.persistence.repository.security.RoleRepository;
import com.example.api.springsecurity.service.PermissionService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private OperationRepository operationRepository;


  @Override
  public Page<ShowPermission> findAll(Pageable pageable) {
    return permissionRepository.findAll(pageable)
            .map(this::mapEntityToShowDto);
  }

  @Override
  public Optional<ShowPermission> findOneById(Long permissionId) {
    return permissionRepository.findById(permissionId)
            .map(this::mapEntityToShowDto);
  }

  @Override
  public ShowPermission createOne(SavePermission savePermission) {
    GrantedPermission newPermission = new GrantedPermission();

    Operation operation = operationRepository.findByName(savePermission.getOperation())
            .orElseThrow(() -> new ObjectNotFoundException("Operation not found. Operation: " + savePermission.getOperation()));
    newPermission.setOperation(operation);

    Role role = roleRepository.findByName(savePermission.getRole()).orElseThrow(
            () -> new ObjectNotFoundException("Role not found. Role: " + savePermission.getRole()));
    newPermission.setRole(role);

    permissionRepository.save(newPermission);
    return this.mapEntityToShowDto(newPermission);
  }

  @Override
  public ShowPermission deleteOneById(Long permissionId) {
    GrantedPermission permission = permissionRepository.findById(permissionId)
            .orElseThrow(() -> new ObjectNotFoundException("Permission not found. Permission: " + permissionId));

    permissionRepository.delete(permission);

    return this.mapEntityToShowDto(permission);
  }

  private ShowPermission mapEntityToShowDto(GrantedPermission grantedPermission) {
    if(grantedPermission == null) return null;

    ShowPermission showDto = new ShowPermission();
    showDto.setId(grantedPermission.getId());
    showDto.setRole(grantedPermission.getRole().getName());
    showDto.setOperation(grantedPermission.getOperation().getName());
    showDto.setHttpMethod(grantedPermission.getOperation().getHttpMethod());
    showDto.setModule(grantedPermission.getOperation().getModule().getName());

    return showDto;
  }
}
