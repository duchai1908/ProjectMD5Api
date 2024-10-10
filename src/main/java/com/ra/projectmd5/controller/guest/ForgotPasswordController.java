package com.ra.projectmd5.controller.guest;

import com.ra.projectmd5.model.dto.request.ForgotPasswordRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IUserService;
import com.ra.projectmd5.model.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final IUserService userService;
    private final MailService mailService;
    @PostMapping
    public ResponseEntity<?> getAccount(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        if(userService.forgotPassword(forgotPasswordRequest)) {
            mailService.sendMail(forgotPasswordRequest.getEmail(), "Forgot Password Confirmation", "Mật khẩu mới: "+userService.passwordReset(forgotPasswordRequest));
        }else{
            throw new NoSuchElementException("Username hoặc email không đúng");
        }
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Lấy mật khẩu thành công, đã gửi mật khẩu qua mail", HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
