package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
