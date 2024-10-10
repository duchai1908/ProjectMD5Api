package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IOrdersService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
}
