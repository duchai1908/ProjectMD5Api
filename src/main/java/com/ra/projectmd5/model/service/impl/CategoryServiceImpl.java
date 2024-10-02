package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.CategoryRequest;
import com.ra.projectmd5.model.entity.Category;
import com.ra.projectmd5.model.repository.ICategoryRepository;
import com.ra.projectmd5.model.service.ICategoryService;
import com.ra.projectmd5.model.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final UploadService uploadService;

    /**
     * Get all category
     * @Param {*} pageable: Pagination, Sort, Search
     * @Param {*} search: Searching value
     * Auth: Duc Hai (01/10/2024)
     * */
    @Override
    public Page<Category> findAll(Pageable pageable, String search) {
        Page<Category> categoryPage;
        if(search == null || search.isEmpty()) {
            categoryPage = categoryRepository.findAll(pageable);
        }else{
            categoryPage = categoryRepository.findCategoryByNameContainsIgnoreCase(search,pageable);
        }
        return categoryPage;
    }

    /**
     * Add new category
     * @Param {*} categoryRequest: Object request from user
     * Auth: Duc Hai (01/10/2024)
     * */
    // Add new Category
    @Override
    public Category save(CategoryRequest categoryRequest) throws DataExistException {
        if(categoryRepository.existsByName(categoryRequest.getName())) {
            throw new DataExistException("Category name has exists","name");
        }
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .status(categoryRequest.getStatus())
                .image(uploadService.uploadFileToServer(categoryRequest.getImage()))
                .build();
        return categoryRepository.save(category);
    }



    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new NoSuchElementException("Category not found"));
    }

    /**
     * get Category by Category id
     * @Param {*} id: Category id
     * Auth: Duc Hai (01/10/2024)
     * */
    @Override
    public Category getCategoryById(Long id) {
        Category category = findById(id);
        return categoryRepository.findCategoryById(id);
    }

    /**
     * Update Category By Id
     * @Param {*} categoryRequest: Object request from user
     * @Param {*} id: Category id
     * Auth: Duc Hai (01/10/2024)
     * */
    // Update Category By Id
    @Override
    public Category update(CategoryRequest categoryRequest, Long id) throws DataExistException {
        Category category = findById(id);
        String multipartFile;
        if(!Objects.equals(categoryRequest.getName(), category.getName()) && categoryRepository.existsByName(categoryRequest.getName())){
            throw new DataExistException("Product name is exists","name");
        }if(categoryRequest.getImage()!=null && categoryRequest.getImage().getSize() >0){
            multipartFile = uploadService.uploadFileToServer(categoryRequest.getImage());
            category.setImage(multipartFile);
        }
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setStatus(categoryRequest.getStatus());
        return categoryRepository.save(category);
    }

    /**
     * Delete Category by id
     * @Param {*} id: Category id
     * Auth: Duc Hai (01/10/2024)
     * */
    // Delete Category By Id
    @Override
    public void deleteById(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
