package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.repository.IColorRepository;
import com.ra.projectmd5.model.service.IColorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements IColorService {
    private final ModelMapper modelMapper;
    private final IColorRepository colorRepository;

    /**
     * Get All Color
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    /**
     * Get Color By Color Id
     * @Param {*} id: Color id
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public Color getColorById(Long id) {
        return colorRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Color not found"));
    }

    /**
     * Add new Color
     * @Param {*} color: Object Color
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public Color save(Color color) throws DataExistException {
        if(colorRepository.existsByColor(color.getColor())){
            throw new DataExistException("Color is exists","color");
        }
        color.setCreated_at(new Date());
        color.setUpdated_at(new Date());
        return colorRepository.save(color);
    }

    /**
     * Update Clor by Id
     * @Param {*} color: Object Color
     * @Param {*} id: Color id
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public Color update(Color color, Long id) throws DataExistException {

        Color colors = getColorById(id);
        if(!Objects.equals(color.getColor(), colors.getColor()) && colorRepository.existsByColor(color.getColor())) {
            throw new DataExistException("Color  is exists", "color");
        }
        colors.setColor(color.getColor());
        colors.setUpdated_at(new Date());
        return colorRepository.save(colors);
    }

    /**
     * Delete color by color id
     * @Param {*} id: Color id
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public void delete(Long id) {
        Color colors = getColorById(id);
        colorRepository.delete(colors);
    }
}
