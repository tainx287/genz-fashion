package com.ecommerce.genzfashion.service;

import com.ecommerce.genzfashion.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    long countAllUsers();
    Page<User> getUsersWithPagination(Pageable pageable);
}