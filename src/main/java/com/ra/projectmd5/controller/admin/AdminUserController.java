package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<?> listAllUsers(@PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
            , @RequestParam(defaultValue = "") String search,
              @RequestParam(defaultValue = "id") String sortField,
              @RequestParam(defaultValue = "ASC") String sortDirection) {
        // Chuyển đổi hướng sắp xếp từ String thành Sort.Direction
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), direction, sortField);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.findAll(pageable, search), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long userId) throws BadRequestException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(userService.changeStatus(userId), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

}
