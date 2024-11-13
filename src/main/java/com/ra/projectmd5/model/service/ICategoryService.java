package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.CategoryRequest;
import com.ra.projectmd5.model.entity.Category;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    Page<Category> findAll(Pageable pageable, String search);
    Category save(CategoryRequest categoryRequest) throws DataExistException;
    Category update(CategoryRequest categoryRequest, Long id) throws DataExistException;
    Category findById(Long id);
    Category getCategoryById(Long id);
    Category changeStatus(Long id);
    List<Category> getCategories();
    void deleteById(Long id) throws BadRequestException;
    List<Category> findAll();
}
