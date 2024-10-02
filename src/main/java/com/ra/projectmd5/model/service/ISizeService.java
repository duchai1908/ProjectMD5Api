package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.entity.Size;

import java.util.List;

public interface ISizeService {
    List<Size> getSizeList();
    Size getSizeById(Long id);
    Size save(Size color) throws DataExistException;
    Size update(Size color, Long id) throws DataExistException;
    void delete(Long id);
}

