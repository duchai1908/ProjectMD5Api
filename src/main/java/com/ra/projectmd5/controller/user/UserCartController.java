package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.request.CartItemRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.ICartItemService;

import com.ra.projectmd5.security.principle.UserDetailCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart")
@RequiredArgsConstructor
public class UserCartController {
    private final ICartItemService cartItemService;
    @GetMapping
    public ResponseEntity<?> getUserListCart(@AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(cartItemService.getAllCartItemsNoPagination(userDetailCustom.getUsers().getId()),HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @GetMapping("/page")
    public ResponseEntity<?> getUserPageCart(@PageableDefault(page = 0,size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,@AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(cartItemService.getAllCartItems(pageable,userDetailCustom.getUsers().getId()),HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody CartItemRequest cartItemRequest, @AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(cartItemService.addToCart(cartItemRequest,userDetailCustom.getUsers().getId()), HttpStatus.CREATED.value(), HttpStatus.CREATED), HttpStatus.CREATED);
    }
    @PutMapping("/changeQuantity/{quantity}/{cartItemId}")
    public ResponseEntity<?> changeQuantity(@PathVariable Integer quantity, @PathVariable Long cartItemId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(cartItemService.changeQuantity(cartItemId,quantity), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Xoá giỏ hàng thành công", HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @DeleteMapping("/deleteAllCart")
    public ResponseEntity<?> deleteAllCartItems(@AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        cartItemService.deleteAllCartItems(userDetailCustom.getUsers().getId());
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Xoá toàn bộ giỏ hàng thành công", HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

}
