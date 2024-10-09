package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.constants.RoleName;
import com.ra.projectmd5.constants.Status;
import com.ra.projectmd5.model.dto.request.ChangePasswordRequest;
import com.ra.projectmd5.model.dto.request.ForgotPasswordRequest;
import com.ra.projectmd5.model.dto.request.UserRequest;
import com.ra.projectmd5.model.entity.Role;
import com.ra.projectmd5.model.entity.User;
import com.ra.projectmd5.model.repository.IUserRepository;
import com.ra.projectmd5.model.service.IUserService;
import com.ra.projectmd5.model.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final UUIDService uuidService;

    /**
     * @Param pageable Pageable
     * @Param search String
     * @apiNote Lấy ra toàn bộ danh sách người dùng
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public Page<User> findAll(Pageable pageable, String search) {
        if (search == null || search.isEmpty()) {
            return userRepository.findAll(pageable);
        }else {
            return userRepository.findByUsernameContainsIgnoreCase(search, pageable);
        }
    }
    /**
     * @Param id Long
     * @apiNote Lấy thông tin chi tiết người dùng theo id
     * @throws RuntimeException Không tìm thấy người dùng
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy người dùng"));
    }

    /**
     * @Param userId
     * @apiNote Thay đổi trạng thái tài khoản
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public User changeStatus(Long userId) throws BadRequestException {
        User user = findById(userId);
        for (Role role : user.getRoles()) {
            if (role.getRoleName().equals(RoleName.ROLE_ADMIN)) {
                throw new BadRequestException("Bạn không thể khoá tài khoản Admin");
            }
        }
        user.setStatus(!user.getStatus());
        return userRepository.save(user);
    }

    @Override
    public User changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        User user = findById(userId);
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        return userRepository.save(user);
    }

    @Override
    public User changeUserInformation(UserRequest userRequest, Long userId) {
        User user = findById(userId);
        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setDob(userRequest.getDob());
        if(userRequest.getImage().getSize() >0){
            user.setImage(uploadService.uploadFileToServer(userRequest.getImage()));
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return userRepository.existsByUsernameAndEmail(forgotPasswordRequest.getUsername(), forgotPasswordRequest.getEmail());
    }

    @Override
    public String passwordReset(ForgotPasswordRequest forgotPasswordRequest) {
        String newPassword = "";
        if(forgotPassword(forgotPasswordRequest)){
            User user = userRepository.findUserByUsername(forgotPasswordRequest.getUsername());
             newPassword = uuidService.couponUUID();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        return newPassword;
    }
}
