package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.FormLogin;
import com.ra.projectmd5.model.dto.request.FormRegister;
import com.ra.projectmd5.model.dto.response.JwtResponse;

public interface IAuthService {
    void register(FormRegister formRegister) throws DataExistException;
    JwtResponse login(FormLogin formLogin);
}
