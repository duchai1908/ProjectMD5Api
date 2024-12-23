package com.ra.projectmd5.controller;

import com.ra.projectmd5.constants.EHttpStatus;
import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.FormLogin;
import com.ra.projectmd5.model.dto.request.FormRegister;
import com.ra.projectmd5.model.dto.response.ResponseWrapper;
import com.ra.projectmd5.model.entity.User;
import com.ra.projectmd5.model.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody FormRegister formRegister) throws DataExistException {
        authService.register(formRegister);
        return new ResponseEntity<>(ResponseWrapper.builder()
                .ehttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data("Register successfully")
                .build(), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody FormLogin formLogin) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .ehttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data(authService.login(formLogin))
                .build(), HttpStatus.CREATED);
    }
}


//    @PostMapping("/sign-in")
//    public ResponseEntity<?> signIn(@RequestBody FormLogin formLogin) {
//        // Bỏ qua việc xác thực và trả về thông tin user "fix cứng"
//        User fixedUser = new User();
//        fixedUser.setId(5L);
//        fixedUser.setUsername("vienvien");
//        fixedUser.setEmail("vienvien@gmail.com");
//        fixedUser.setFullName("vien nguyen");
//
//        // Trả về user cố định
//        return new ResponseEntity<>(ResponseWrapper.builder()
//                .ehttpStatus(EHttpStatus.SUCCESS)
//                .statusCode(HttpStatus.OK.value()) // 200 OK
//                .data(fixedUser) // Trả về thông tin user cố định
//                .build(), HttpStatus.OK);
//    }
//}
