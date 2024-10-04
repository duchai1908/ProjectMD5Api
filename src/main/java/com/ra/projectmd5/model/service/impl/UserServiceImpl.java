package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.entity.User;
import com.ra.projectmd5.model.repository.IUserRepository;
import com.ra.projectmd5.model.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable, String search) {
        if (search == null || search.isEmpty()) {
            return userRepository.findAll(pageable);
        }else {
            return userRepository.findByUsernameContainsIgnoreCase(search, pageable);
        }
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy người dùng"));
    }
}
