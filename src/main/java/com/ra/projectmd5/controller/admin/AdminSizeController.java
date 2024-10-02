package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.ISizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/size")
@RequiredArgsConstructor
public class AdminSizeController {
    private final ISizeService sizeService;
    @GetMapping
    public ResponseEntity<?> getAllSize() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(sizeService.getSizeList(), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
}
