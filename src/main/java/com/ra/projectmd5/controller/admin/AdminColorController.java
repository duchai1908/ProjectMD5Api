package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.service.IColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/color")
@RequiredArgsConstructor
public class AdminColorController {
    private final IColorService colorService;
    @GetMapping
    public ResponseEntity<?> getColor() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(colorService.getAllColors(), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @GetMapping("/{colorId}")
    public ResponseEntity<?> getColorById(@PathVariable Long colorId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(colorService.getColorById(colorId), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addColor(@RequestBody Color color) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(colorService.save(color),HttpStatus.CREATED.value(), HttpStatus.CREATED),HttpStatus.CREATED);
    }
    @PutMapping("/{colorId}")
    public ResponseEntity<?> updateColor(@RequestBody Color color, @PathVariable Long colorId) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(colorService.update(color, colorId),HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @DeleteMapping("/{colorId}")
    public ResponseEntity<?> deleteColor(@PathVariable Long colorId){
        colorService.delete(colorId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Delete color successfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
    }
}
