package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.dto.request.AddressRequest;
import com.ra.projectmd5.model.entity.Address;
import com.ra.projectmd5.model.entity.User;
import com.ra.projectmd5.model.repository.IAddressRepository;
import com.ra.projectmd5.model.service.IAddressService;
import com.ra.projectmd5.model.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
    private final IAddressRepository iAddressRepository;
    private final IUserService iUserService;
    @Override
    public List<Address> listAddress(Long userId) {
        User user = iUserService.findById(userId);
        return iAddressRepository.findByUserId(userId);
    }

    @Override
    public Address addAddress(AddressRequest addressRequest, Long userId) {
        User user = iUserService.findById(userId);
        Address address = Address.builder()
                .address(addressRequest.getAddress())
                .phone(addressRequest.getPhone())
                .receiveName(addressRequest.getReceiveName())
                .user(user)
                .build();
        return iAddressRepository.save(address);
    }

    @Override
    public Address updateAddress(AddressRequest addressRequest, Long addressId) {
        Address address = getAddress(addressId);
        address.setAddress(addressRequest.getAddress());
        address.setPhone(addressRequest.getPhone());
        address.setReceiveName(addressRequest.getReceiveName());
        return null;
    }

    @Override
    public Address getAddress(Long addressId) {
        return iAddressRepository.findById(addressId).orElseThrow(()-> new NoSuchElementException("Không tìm thấy địa chỉ"));
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = getAddress(addressId);
        iAddressRepository.delete(address);
    }
}
