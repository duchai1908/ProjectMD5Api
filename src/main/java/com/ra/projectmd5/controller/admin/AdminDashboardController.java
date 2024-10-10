package com.ra.projectmd5.controller.admin;


import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final IDashboardService dashboardService;
    @GetMapping
    public ResponseEntity<?> getDashboard() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(dashboardService.totalDashboard(), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
}
