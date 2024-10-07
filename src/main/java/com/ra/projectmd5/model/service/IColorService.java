package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.CustomException;
import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IColorService {
    List<Color> getAllColors();
    Color getColorById(Long id);
    Color save(Color color) throws DataExistException;
    Color update(Color color, Long id) throws DataExistException;
    void delete(Long id) throws CustomException;
    Page<Color> searchColors(Pageable pageable, String color, String sortOption);

}
