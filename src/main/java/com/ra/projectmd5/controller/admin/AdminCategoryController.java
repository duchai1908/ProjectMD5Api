package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.CategoryRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final ICategoryService categoryService;

    // List Category
    //api/v1/admin/category?page=0&size=4&sortField=name&sortDirection=ASC
    @GetMapping
    public ResponseEntity<?> getAllCategory(
            @PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "id") String sortField, // Thêm tham số sortField
            @RequestParam(defaultValue = "ASC") String sortDirection // Thêm tham số sortDirection
    ) {
        // Chuyển đổi hướng sắp xếp từ String thành Sort.Direction
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), direction, sortField);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryService.findAll(pageable, search),HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }

    //Get category by id
    @GetMapping("/{cateId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long cateId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryService.getCategoryById(cateId),HttpStatus.OK.value(),HttpStatus.OK), HttpStatus.OK);
    }
    // Add new category
    @PostMapping
    public ResponseEntity<?> addCategory(@ModelAttribute CategoryRequest categoryRequest) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryService.save(categoryRequest),HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

    // Update cate by Id
    @PutMapping("/{cateId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long cateId, @ModelAttribute CategoryRequest categoryRequest) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryService.update(categoryRequest,cateId),HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

    // Delete cate by Id
    @DeleteMapping("/{cateId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long cateId) throws BadRequestException {
        categoryService.deleteById(cateId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Delete Successly",HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    // Change status by id
    @PutMapping("/changeStatus/{cateId}")
    public ResponseEntity<?> changeCategoryStatus(@PathVariable Long cateId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryService.changeStatus(cateId),HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
}
