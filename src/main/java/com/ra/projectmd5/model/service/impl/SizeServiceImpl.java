package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.entity.Size;
import com.ra.projectmd5.model.repository.ISizeRepository;
import com.ra.projectmd5.model.service.ISizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements ISizeService {
    private final ISizeRepository sizeRepository;
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
            throw new DataExistException("Size is exists","color");
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
            throw new DataExistException("Size is exists", "color");
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
    public void delete(Long id) {
        Size sizes = getSizeById(id);
        sizeRepository.delete(sizes);
    }
}
