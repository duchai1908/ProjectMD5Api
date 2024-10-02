package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.ProductRequest;
import com.ra.projectmd5.model.entity.Product;
import com.ra.projectmd5.model.repository.IProductRepository;
import com.ra.projectmd5.model.service.ICategoryService;
import com.ra.projectmd5.model.service.IProductService;
import com.ra.projectmd5.model.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryService categoryService;
    private final UploadService uploadService;
    /**
     * @Param pageable Pageable
     * @Param {*} search: thông tin vào dữ liệu cần tìm kiếm
     * @apiNote Lấy ra danh sách toàn bộ sản phẩm
     * Auth: Duc Hai (02/10/2024)
     */
    @Override
    public Page<Product> findAll(Pageable pageable, String search) {
        Page<Product> products;
        if(search == null || search.isEmpty()) {
            products =  productRepository.findAll(pageable);
        }else{
            products = productRepository.findProductByNameContainsIgnoreCase(search, pageable);
        }
        return products;
    }

    /**
     * @Param id Long
     * @apiNote Lấy ra
     * @throws NoSuchElementException không tìm thấy sản phẩm
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new NoSuchElementException("Không tìm thấy sản phẩm có id: "+id));
    }

    /**
     * @Param productName String
     * @apiNote Kiểm tra xem tên sản phẩm có tồn tại hay không
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public boolean existsByProductName(String productName) {
        return productRepository.existsByName(productName);
    }

    /**
     * @Param productRequest ProductRequest
     * @apiNote Thêm mới sản phẩm
     * @throws DataExistException sản phẩm đã tồn tại
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public Product saveProduct(ProductRequest productRequest) throws DataExistException {
        if(existsByProductName(productRequest.getName())) {
            throw new DataExistException("Tên sản phẩm đã tồn tại","name");
        }
        Product product = Product.builder()
                .name(productRequest.getName())
                .sell(0)
                .category(categoryService.getCategoryById(productRequest.getCategoryId()))
                .created_at(new Date())
                .updated_at(new Date())
                .image(uploadService.uploadFileToServer(productRequest.getImage()))
                .status(true)
                .sale(0)
                .build();
        return productRepository.save(product);
    }

    /**
     * @Param id Long
     * @Param productRequest ProductRequest
     * @apiNote Sửa thông tin của sản phẩm dựa theo id sản phẩm
     * @throws DataExistException sản phẩm đã tồn tại
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) throws DataExistException {
        Product product = getProductById(id);
        String multipartFile;
        if(!Objects.equals(productRequest.getName(), product.getName()) && existsByProductName(productRequest.getName())){
            throw new DataExistException("Tên sản phẩm đã tồn tại","name");
        }
        if(productRequest.getImage()!=null && productRequest.getImage().getSize() >0){
            multipartFile = uploadService.uploadFileToServer(productRequest.getImage());
            product.setImage(multipartFile);
        }
        product.setName(productRequest.getName());
        product.setSell(0);
        product.setCategory(categoryService.getCategoryById(productRequest.getCategoryId()));
        product.setSale(0);
        product.setStatus(productRequest.isStatus());
        product.setUpdated_at(new Date());
        return productRepository.save(product);
    }

    /**
     * @Param id Long
     * @apiNote xoá sản phẩm dựa theo id sản phẩm
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
