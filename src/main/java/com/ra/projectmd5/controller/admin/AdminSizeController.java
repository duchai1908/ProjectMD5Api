package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.exception.CustomException;
import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.entity.Size;
import com.ra.projectmd5.model.service.ISizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1/admin/size")

@RequiredArgsConstructor
public class AdminSizeController {
    private final ISizeService sizeService;
    @GetMapping
    public ResponseEntity<?> getAllSize() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(sizeService.getSizeList(), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @GetMapping("/api/v1/{sizeId}")
    public ResponseEntity<?> getSizeById(@PathVariable Long sizeId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(sizeService.getSizeById(sizeId), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewSize(@RequestBody Size size) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(sizeService.save(size),HttpStatus.CREATED.value(),HttpStatus.CREATED),HttpStatus.CREATED);
    }

    @PutMapping("/{sizeId}")
    public ResponseEntity<?> updateSize(@PathVariable Long sizeId, @RequestBody Size size) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(sizeService.update(size,sizeId),HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
    }

    @DeleteMapping("/{sizeId}")
    public ResponseEntity<?> deleteSize(@PathVariable Long sizeId) throws CustomException {
        sizeService.delete(sizeId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Delete size successfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping("/listSizes")
    public ResponseEntity<?> getAllColorsWithSearch(
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "none") String sortOption) {

        // Chuyển sortOption sang service để xử lý logic sắp xếp
        Page<Size> sizes = sizeService.searchSizes(pageable, search, sortOption);

        return new ResponseEntity<>(new ResponseDtoSuccess<>(sizes, HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
