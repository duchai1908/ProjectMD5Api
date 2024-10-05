package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.request.OrderRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IOrdersService;
import com.ra.projectmd5.security.principle.UserDetailCustom;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/orders")
@RequiredArgsConstructor
public class UserOrderController {
    private final IOrdersService ordersService;
    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @AuthenticationPrincipal UserDetailCustom userDetailCustom
            ){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ordersService.findAllOrdersByUser(pageable,search,userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> checkOut(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal UserDetailCustom userDetailCustom){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ordersService.addToOrders(orderRequest,userDetailCustom.getUsers().getId()), HttpStatus.CREATED.value(), HttpStatus.CREATED), HttpStatus.CREATED);
    }
    @GetMapping("/{ordersId}")
    public ResponseEntity<?> getOrders(@PathVariable Long ordersId, @AuthenticationPrincipal UserDetailCustom userDetailCustom){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ordersService.getOrdersById(ordersId,userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @PutMapping("/cancel/{ordersId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long ordersId, @AuthenticationPrincipal UserDetailCustom userDetailCustom) throws BadRequestException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ordersService.cancelOrder(ordersId,userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

}
