package com.example.secondhand.controller;

import com.example.secondhand.entity.Transaction;
import com.example.secondhand.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody Transaction transaction) {
        Map<String, Object> result = new HashMap<>();
        try {
            Transaction createdTransaction = transactionService.create(transaction);
            result.put("success", true);
            result.put("message", "交易创建成功");
            result.put("transaction", createdTransaction);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "交易创建失败：" + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/update-status/{id}")
    public Map<String, Object> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            Transaction updatedTransaction = transactionService.updateStatus(id, status);
            if (updatedTransaction != null) {
                result.put("success", true);
                result.put("message", "状态更新成功");
                result.put("transaction", updatedTransaction);
            } else {
                result.put("success", false);
                result.put("message", "交易不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "状态更新失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Transaction transaction = transactionService.findById(id);
            if (transaction != null) {
                result.put("success", true);
                result.put("transaction", transaction);
            } else {
                result.put("success", false);
                result.put("message", "交易不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/buyer/{buyerId}")
    public Map<String, Object> findByBuyerId(@PathVariable Long buyerId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Transaction> transactions = transactionService.findByBuyerId(buyerId);
            result.put("success", true);
            result.put("transactions", transactions);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/seller/{sellerId}")
    public Map<String, Object> findBySellerId(@PathVariable Long sellerId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Transaction> transactions = transactionService.findBySellerId(sellerId);
            result.put("success", true);
            result.put("transactions", transactions);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/product/{productId}")
    public Map<String, Object> findByProductId(@PathVariable Long productId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Transaction> transactions = transactionService.findByProductId(productId);
            result.put("success", true);
            result.put("transactions", transactions);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
}