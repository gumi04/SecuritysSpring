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
 * Nombre de archivo: CategoryServiceImpl
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.service.impl;

import com.example.api.springsecurity.constants.CategoryStatus;
import com.example.api.springsecurity.dto.CategoryDto;
import com.example.api.springsecurity.exception.ObjectNotFoundException;
import com.example.api.springsecurity.persistence.entity.Category;
import com.example.api.springsecurity.persistence.repository.CategoryRepository;
import com.example.api.springsecurity.service.CategoryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The type Category service.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

  /**
   * The Category repository.
   */
  @Autowired
  private CategoryRepository categoryRepository;

  /**
   * Fin all page.
   *
   * @param pageable the pageable
   * @return the page
   */
  @Override
  public Page<Category> finAll(Pageable pageable) {
    return categoryRepository.findAll(pageable);
  }

  /**
   * Fin by id optional.
   *
   * @param id the id
   * @return the optional
   */
  @Override
  public Optional<Category> finById(Long id) {
    return categoryRepository.findById(id);
  }

  /**
   * Save category.
   *
   * @param categoryDto the category dto
   * @return the category
   */
  @Override
  public Category save(CategoryDto categoryDto) {
    Category category = new Category();
    category.setName(categoryDto.getName());
    category.setStatus(CategoryStatus.ENABLED);
    return categoryRepository.save(category);
  }

  /**
   * Update category.
   *
   * @param id          the id
   * @param categoryDto the category dto
   * @return the category
   */
  @Override
  public Category update(Long id, CategoryDto categoryDto) {
    Category categoryDb = categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Category not Found"));
    categoryDb.setName(categoryDto.getName());
    return categoryRepository.save(categoryDb);
  }

  /**
   * Disable product category.
   *
   * @param id the id
   * @return the category
   */
  @Override
  public Category disableProduct(Long id) {
    Category categoryDb = categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Category not Found"));
    categoryDb.setStatus(CategoryStatus.DISABLE);
    return categoryRepository.save(categoryDb);
  }
}
