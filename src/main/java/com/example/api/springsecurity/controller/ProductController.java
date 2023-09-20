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

import com.example.api.springsecurity.dto.ProductDto;
import com.example.api.springsecurity.persistence.entity.Product;
import com.example.api.springsecurity.service.ProductService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/products")
@Tag(
        name = "Productos",
        description = "Operaciones relacionadas con los productos")
public class ProductController {

  /**
   * The Product service.
   */
  @Autowired
  private ProductService productService;


  /**
   * Find all response entity.
   *
   * @param pageable the pageable
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
  @Operation(summary = "obtener los productos paginados", security = {@SecurityRequirement(name = "bearer")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Product>> findAll(Pageable pageable) {

    Page<Product> products = productService.finAll(pageable);

    if (products.hasContent()) {
      return ResponseEntity.ok(products);
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Find byid response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
  @Operation(summary = "obtener producto por el id", security = {@SecurityRequirement(name = "bearer")})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> findByid(@PathVariable Long id) {

    Optional<Product> product = productService.finById(id);

    return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Create response entity.
   *
   * @param productDto the product dto
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
  @Operation(summary = "crear producto", security = {@SecurityRequirement(name = "bearer")})
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> create(@RequestBody @Valid ProductDto productDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
  }

  /**
   * Update response entity.
   *
   * @param id         the id
   * @param productDto the product dto
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
  @Operation(summary = "actualizar producto", security = {@SecurityRequirement(name = "bearer")})
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
    return ResponseEntity.ok(productService.update(id, productDto));
  }

  /**
   * Disable by id response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')")
  @Operation(summary = "desabilitar producto", security = {@SecurityRequirement(name = "bearer")})
  @PutMapping(value = "/{id}/disabled", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> disableById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.disableProduct(id));
  }
}
