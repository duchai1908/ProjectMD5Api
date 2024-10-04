package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final IUserService userService;
    @GetMapping
    public ResponseEntity<?> listAllUsers(@PageableDefault(page = 0,size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "" )String search) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.findAll(pageable,search), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
