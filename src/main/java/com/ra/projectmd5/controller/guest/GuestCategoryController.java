package com.ra.projectmd5.controller.guest;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.ICategoryService;
import com.ra.projectmd5.model.service.IColorService;
import com.ra.projectmd5.model.service.IProductService;
import com.ra.projectmd5.model.service.ISizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class GuestCategoryController {
    private final ICategoryService categoryService;
    private final IColorService colorService;
    private final ISizeService sizeService;

    @GetMapping("/listCategory")
    public ResponseEntity<?> getListCategory() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryService.findAll(), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/listCategory/limit")
    public ResponseEntity<?> getListCategoryLimit() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryService.getCategories(),HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/listColor")
    public ResponseEntity<?> getListColor() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(colorService.getAllColors(), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/listSize")
    public ResponseEntity<?> getListSize() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(sizeService.getSizeList(), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
