package com.example.secondhand.service;

import com.example.secondhand.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction create(Transaction transaction);
    Transaction updateStatus(Long id, Integer status);
    Transaction findById(Long id);
    List<Transaction> findByBuyerId(Long buyerId);
    List<Transaction> findBySellerId(Long sellerId);
    List<Transaction> findByProductId(Long productId);
}