package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.request.ChangePasswordRequest;
import com.ra.projectmd5.model.dto.request.UserRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IUserService;
import com.ra.projectmd5.security.principle.UserDetailCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/information")
@RequiredArgsConstructor
public class UserInforController {
    private final IUserService userService;
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.changePassword(userDetailCustom.getUsers().getId(), changePasswordRequest), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @PutMapping("/change")
    public ResponseEntity<?> changeInformationUser(@AuthenticationPrincipal UserDetailCustom userDetailCustom, @ModelAttribute UserRequest userRequest) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.changeUserInformation(userRequest,userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
