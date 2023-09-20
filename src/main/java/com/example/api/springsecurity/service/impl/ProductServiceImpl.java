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
 * Nombre de archivo: ProductServiceImpl
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.service.impl;

import com.example.api.springsecurity.constants.ProductStatus;
import com.example.api.springsecurity.dto.ProductDto;
import com.example.api.springsecurity.exception.ObjectNotFoundException;
import com.example.api.springsecurity.persistence.entity.Category;
import com.example.api.springsecurity.persistence.entity.Product;
import com.example.api.springsecurity.persistence.repository.ProductRepository;
import com.example.api.springsecurity.service.ProductService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The type Product service.
 */
@Service
public class ProductServiceImpl implements ProductService {
  /**
   * The Product repository.
   */
  @Autowired
  private ProductRepository productRepository;

  /**
   * Fin all page.
   *
   * @param page the page
   * @return the page
   */
  @Override
  public Page<Product> finAll(Pageable page) {
    return productRepository.findAll(page);
  }

  /**
   * Fin by id optional.
   *
   * @param id the id
   * @return the optional
   */
  @Override
  public Optional<Product> finById(Long id) {
    return productRepository.findById(id);
  }

  /**
   * Save product.
   *
   * @param productDto the product dto
   * @return the product
   */
  @Override
  public Product save(ProductDto productDto) {
    Product product = new Product();
    product.setName(productDto.getName());
    product.setStatus(ProductStatus.ENABLED);
    product.setPrice(productDto.getPrice());

    Category category = new Category();
    category.setId(productDto.getCategoryId());
    product.setCategory(category);
    return productRepository.save(product);
  }

  /**
   * Update product.
   *
   * @param id         the id
   * @param productDto the product dto
   * @return the product
   */
  @Override
  public Product update(Long id, ProductDto productDto) {
    Product productDb = productRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

    productDb.setName(productDto.getName());
    productDb.setPrice(productDto.getPrice());

    Category category = new Category();
    category.setId(productDto.getCategoryId());
    productDb.setCategory(category);
    return productRepository.save(productDb);
  }

  /**
   * Disable product product.
   *
   * @param id the id
   * @return the product
   */
  @Override
  public Product disableProduct(Long id) {
    Product productDb = productRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Product not found"));
    productDb.setStatus(ProductStatus.DISABLE);
    return productRepository.save(productDb);
  }
}
