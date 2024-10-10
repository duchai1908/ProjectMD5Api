package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IOrdersService;
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
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final IOrdersService ordersService;
    @PutMapping("/{orderId}")
    public ResponseEntity<?> deniedOrder(@PathVariable Long orderId) throws BadRequestException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ordersService.deniedOrder(orderId), HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "id") String sortField, // Thêm tham số sortField
            @RequestParam(defaultValue = "ASC") String sortDirection // Thêm tham số sortDirection
    ){
        // Chuyển đổi hướng sắp xếp từ String thành Sort.Direction
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), direction, sortField);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ordersService.findAll(pageable, search),HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/{orderId}/{status}")
    public ResponseEntity<?> changeStatusOrder(@PathVariable Long orderId, @PathVariable String status){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ordersService.changeStatus(orderId, status), HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }
}
