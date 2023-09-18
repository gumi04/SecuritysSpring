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
 * Nombre de archivo: ProductController
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.controller;

import com.example.api.springsecurity.dto.CategoryDto;
import com.example.api.springsecurity.persistence.entity.Category;
import com.example.api.springsecurity.service.CategoryService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Tag(
        name = "Category",
        description = "Crud of categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;


  @Operation(summary = "obtener categorias paginados", security = {@SecurityRequirement(name = "bearer")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Category>> findAll(Pageable pageable) {

    Page<Category> categories = categoryService.finAll(pageable);

    if (categories.hasContent()) {
      return ResponseEntity.ok(categories);
    }
    return ResponseEntity.notFound().build();
  }

  @Operation(summary = "obtener categoria por su id", security = {@SecurityRequirement(name = "bearer")})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> findByid(@PathVariable Long id) {

    Optional<Category> category = categoryService.finById(id);

    return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "crear una categoria", security = {@SecurityRequirement(name = "bearer")})
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> create(@RequestBody @Valid CategoryDto categoryDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDto));
  }

  @Operation(summary = "actualizar categoria", security = {@SecurityRequirement(name = "bearer")})
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto) {
    return ResponseEntity.ok(categoryService.update(id, categoryDto));
  }

  @Operation(summary = "desabilitar categoria", security = {@SecurityRequirement(name = "bearer")})
  @PutMapping(value = "/{id}/disabled", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> disableById(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.disableProduct(id));
  }
}
