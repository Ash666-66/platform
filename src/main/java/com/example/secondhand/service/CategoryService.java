package com.example.secondhand.service;

import com.example.secondhand.entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category update(Category category);
    void delete(Integer id);
    Category findById(Integer id);
    List<Category> findAll();
    List<Category> findByParentId(Integer parentId);
}