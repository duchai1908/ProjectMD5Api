package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.dto.request.ChangePasswordRequest;
import com.ra.projectmd5.model.dto.request.ForgotPasswordRequest;
import com.ra.projectmd5.model.dto.request.UserRequest;
import com.ra.projectmd5.model.entity.User;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    Page<User> findAll(Pageable pageable,String search);
    User findById(Long id);
    User changeStatus(Long userId) throws BadRequestException;
    User changePassword(Long userId, ChangePasswordRequest changePasswordRequest);
    User changeUserInformation(UserRequest userRequest, Long userId);
    List<User> findAllUsers();
    boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    String passwordReset(ForgotPasswordRequest forgotPasswordRequest);
}
