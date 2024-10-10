package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.CustomException;
import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISizeService {
    List<Size> getSizeList();
    Size getSizeById(Long id);
    Size save(Size size) throws DataExistException;
    Size update(Size size, Long id) throws DataExistException;
    void delete(Long id) throws CustomException;
    Page<Size> searchSizes(Pageable pageable, String size, String sortOption);
}

