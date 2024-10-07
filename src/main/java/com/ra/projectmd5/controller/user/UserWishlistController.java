package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IWishlistService;
import com.ra.projectmd5.security.principle.UserDetailCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/wishlist")
@RequiredArgsConstructor
public class UserWishlistController {
    private final IWishlistService wishlistService;
    @GetMapping
    public ResponseEntity<?> getWishlist(@AuthenticationPrincipal UserDetailCustom userDetail) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(wishlistService.getWishlist(userDetail.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @PostMapping("/{productId}")
    public ResponseEntity<?> addToWishlist(@PathVariable Long productId, @AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        wishlistService.addToWishlist(productId,userDetailCustom.getUsers().getId());
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Thêm thành công", HttpStatus.CREATED.value(), HttpStatus.CREATED), HttpStatus.CREATED);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductFromWishlist(@PathVariable Long productId, @AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>( wishlistService.deleteProductFromWishlist(productId,userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteAllWishlist(@AuthenticationPrincipal UserDetailCustom userDetailCustom) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(wishlistService.deleteAllProductsFromWishlist(userDetailCustom.getUsers().getId()), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
