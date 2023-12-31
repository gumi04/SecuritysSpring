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
 * Nombre de archivo: ProductService
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.service;

import com.example.api.springsecurity.dto.ProductDto;
import com.example.api.springsecurity.persistence.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Product service.
 */
public interface ProductService {

  /**
   * Fin all page.
   *
   * @param page the page
   * @return the page
   */
  Page<Product> finAll(Pageable page);

  /**
   * Fin by id optional.
   *
   * @param id the id
   * @return the optional
   */
  Optional<Product> finById(Long id);

  /**
   * Save product.
   *
   * @param productDto the product dto
   * @return the product
   */
  Product save(ProductDto productDto);

  /**
   * Update product.
   *
   * @param id         the id
   * @param productDto the product dto
   * @return the product
   */
  Product update(Long id, ProductDto productDto);

  /**
   * Disable product product.
   *
   * @param id the id
   * @return the product
   */
  Product disableProduct(Long id);
}
