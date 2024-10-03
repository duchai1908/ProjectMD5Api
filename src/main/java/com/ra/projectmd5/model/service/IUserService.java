package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> findAll(Pageable pageable,String search);
    User findById(Long id);
}
