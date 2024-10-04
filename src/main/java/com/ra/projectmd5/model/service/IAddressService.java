package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.dto.request.AddressRequest;
import com.ra.projectmd5.model.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IAddressService {
    List<Address> listAddress(Long userId);
    Address addAddress(AddressRequest addressRequest, Long userId);
    Address updateAddress(AddressRequest addressRequest, Long addressId);
    Address getAddress(Long addressId);
    void deleteAddress(Long addressId);
}
