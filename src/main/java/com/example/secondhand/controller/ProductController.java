package com.example.secondhand.controller;

import com.example.secondhand.entity.Product;
import com.example.secondhand.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @PostMapping("/publish")
    public Map<String, Object> publish(@RequestBody Product product) {
        Map<String, Object> result = new HashMap<>();
        try {
            Product publishedProduct = productService.publish(product);
            result.put("success", true);
            result.put("message", "发布成功");
            result.put("product", publishedProduct);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "发布失败：" + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Product product) {
        Map<String, Object> result = new HashMap<>();
        try {
            Product updatedProduct = productService.update(product);
            result.put("success", true);
            result.put("message", "更新成功");
            result.put("product", updatedProduct);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败：" + e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            productService.delete(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Product product = productService.findById(id);
            if (product != null) {
                result.put("success", true);
                result.put("product", product);
            } else {
                result.put("success", false);
                result.put("message", "商品不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/user/{userId}")
    public Map<String, Object> findByUserId(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Product> products = productService.findByUserId(userId);
            result.put("success", true);
            result.put("products", products);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/category/{categoryId}")
    public Map<String, Object> findByCategoryId(@PathVariable Integer categoryId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Product> products = productService.findByCategoryId(categoryId);
            result.put("success", true);
            result.put("products", products);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam String keyword) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Product> products = productService.search(keyword);
            result.put("success", true);
            result.put("products", products);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "搜索失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/list")
    public Map<String, Object> findAll() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Product> products = productService.findAll();
            result.put("success", true);
            result.put("products", products);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
}