package com.example.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.secondhand.entity.User;

public interface UserMapper extends BaseMapper<User> {
    User findByUsername(String username);
    User findByEmail(String email);
}