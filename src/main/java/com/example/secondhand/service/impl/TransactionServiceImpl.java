package com.example.secondhand.service.impl;

import com.example.secondhand.entity.Transaction;
import com.example.secondhand.mapper.TransactionMapper;
import com.example.secondhand.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;
    
    @Override
    public Transaction create(Transaction transaction) {
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus(1); // 初始状态为待确认
        transactionMapper.insert(transaction);
        return transaction;
    }
    
    @Override
    public Transaction updateStatus(Long id, Integer status) {
        Transaction transaction = transactionMapper.selectById(id);
        if (transaction != null) {
            transaction.setStatus(status);
            transaction.setUpdatedAt(LocalDateTime.now());
            transactionMapper.updateById(transaction);
        }
        return transaction;
    }
    
    @Override
    public Transaction findById(Long id) {
        return transactionMapper.selectById(id);
    }
    
    @Override
    public List<Transaction> findByBuyerId(Long buyerId) {
        return transactionMapper.findByBuyerId(buyerId);
    }
    
    @Override
    public List<Transaction> findBySellerId(Long sellerId) {
        return transactionMapper.findBySellerId(sellerId);
    }
    
    @Override
    public List<Transaction> findByProductId(Long productId) {
        return transactionMapper.findByProductId(productId);
    }
}