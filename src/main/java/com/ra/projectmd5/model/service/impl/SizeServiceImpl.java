package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.CustomException;
import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.entity.Size;
import com.ra.projectmd5.model.repository.IProductDetailRepository;
import com.ra.projectmd5.model.repository.ISizeRepository;
import com.ra.projectmd5.model.service.ISizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements ISizeService {
    private final ISizeRepository sizeRepository;
    private final IProductDetailRepository productDetailRepository;
    /**
     * Get All Size
     * Auth:Duc Hai (02/10/2024)
     * */
    @Override
    public List<Size> getSizeList() {
        return sizeRepository.findAll();
    }
    /**
     * Get Size by Id
     * @Param {*} id: Size Id
     * Auth:Duc Hai (02/10/2024)
     * */
    @Override
    public Size getSizeById(Long id) {
        return sizeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Size not found"));
    }

    /**
     * Add new Size
     * @Param {*} size: Object Size
     * Auth:Duc Hai (02/10/2024)
     * */
    @Override
    public Size save(Size size) throws DataExistException {
        if(sizeRepository.existsBySize(size.getSize())){
            throw new DataExistException("Size is exists","size");
        }
        size.setCreated_at(new Date());
        size.setUpdated_at(new Date());
        return sizeRepository.save(size);
    }

    /**
     * Update Size by Id
     * @Param {*} size: Object Size
     * @Param {*} id: Size id
     * Auth:Duc Hai (02/10/2024)
     * */
    @Override
    public Size update(Size size, Long id) throws DataExistException {

        Size sizes = getSizeById(id);
        if(!Objects.equals(size.getSize(), sizes.getSize()) && sizeRepository.existsBySize(size.getSize())) {
            throw new DataExistException("Size is exists", "size");
        }
        sizes.setSize(size.getSize());
        sizes.setUpdated_at(new Date());
        return sizeRepository.save(sizes);
    }

    /**
     * Delete Size by Id
     * @Param {*}: size id
     * Auth:Duc Hai (02/10/2024)
     * */
    @Override
    public void delete(Long id) throws CustomException {
        if (sizeRepository.existsById(id)) {
            if (productDetailRepository.existsBySizeId(id)) {
                throw new CustomException("Kích cỡ này có được dùng trong sản phẩm.", HttpStatus.CONFLICT);
            } else {
                sizeRepository.deleteById(id);
            }
        } else {
            throw new CustomException("color not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Page<Size> searchSizes(Pageable pageable, String size, String sortOption) {
        Sort sort = switch (sortOption) {
            case "aToZ" -> Sort.by(Sort.Direction.ASC, "size");
            case "zToA" -> Sort.by(Sort.Direction.DESC, "size");
            default -> pageable.getSort();
        };

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Size> sizePage;
        if (size == null || size.isEmpty()) {
            sizePage = sizeRepository.findAll(sortedPageable);
        } else {
            sizePage = sizeRepository.findAllBySizeContainsIgnoreCase(size, sortedPageable);
        }

        return sizePage;
    }
}
