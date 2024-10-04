package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.request.AddressRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IAddressService;
import com.ra.projectmd5.security.principle.UserDetailCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/address")
@RequiredArgsConstructor
public class UserAddressController {
    private final IAddressService addressService;
    @GetMapping
    public ResponseEntity<?> getAllAddress(@AuthenticationPrincipal UserDetailCustom userDetailCustom){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(addressService.listAddress(userDetailCustom.getUsers().getId()), HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(addressService.getAddress(addressId), HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest addressRequest, @AuthenticationPrincipal UserDetailCustom userDetailCustom){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(addressService.addAddress(addressRequest, userDetailCustom.getUsers().getId()), HttpStatus.CREATED.value(),HttpStatus.CREATED), HttpStatus.CREATED);
    }
    @PutMapping("/{addressId}")
    public ResponseEntity<?> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable Long addressId){
        return new ResponseEntity<>(new ResponseDtoSuccess<>(addressService.updateAddress(addressRequest, addressId), HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }
    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId){
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Xoá địa chỉ thành công",HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }

}
