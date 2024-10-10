package com.ra.projectmd5.model.service.impl;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDService {
    public String couponUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        return uuidString.substring(0,8);
    }
}
