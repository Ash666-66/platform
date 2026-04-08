package com.example.secondhand.controller;

import com.example.secondhand.entity.Category;
import com.example.secondhand.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody Category category) {
        Map<String, Object> result = new HashMap<>();
        try {
            Category createdCategory = categoryService.create(category);
            result.put("success", true);
            result.put("message", "创建成功");
            result.put("category", createdCategory);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "创建失败：" + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Category category) {
        Map<String, Object> result = new HashMap<>();
        try {
            Category updatedCategory = categoryService.update(category);
            result.put("success", true);
            result.put("message", "更新成功");
            result.put("category", updatedCategory);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            categoryService.delete(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Category category = categoryService.findById(id);
            if (category != null) {
                result.put("success", true);
                result.put("category", category);
            } else {
                result.put("success", false);
                result.put("message", "分类不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/list")
    public Map<String, Object> findAll() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Category> categories = categoryService.findAll();
            result.put("success", true);
            result.put("categories", categories);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/parent/{parentId}")
    public Map<String, Object> findByParentId(@PathVariable Integer parentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Category> categories = categoryService.findByParentId(parentId);
            result.put("success", true);
            result.put("categories", categories);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
}