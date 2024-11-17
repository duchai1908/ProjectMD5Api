package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.request.ChangePasswordRequest;
import com.ra.projectmd5.model.dto.request.UserRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IChatMessageService;
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
    private final IChatMessageService chatMessageService;
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.changePassword(userDetailCustom.getUsers().getId(), changePasswordRequest), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @PutMapping("/change")
    public ResponseEntity<?> changeInformationUser(@AuthenticationPrincipal UserDetailCustom userDetailCustom, @ModelAttribute UserRequest userRequest) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.changeUserInformation(userRequest,userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/listUser")
    public ResponseEntity<?> listUser() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.findAllUsers(), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.findById(userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/getUsername/{id}")
    public ResponseEntity<?> getUsername(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.findById(id), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("getHistory/{receiverId}")
    public ResponseEntity<?> getHistory(@PathVariable("receiverId") Long receiverId, @AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(chatMessageService.getHistory(userDetailCustom.getUsers().getId(), receiverId), HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }
}
