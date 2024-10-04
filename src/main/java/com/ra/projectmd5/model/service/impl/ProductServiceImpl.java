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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
//    @Override
//    public Page<Product> findAll(Pageable pageable, String search) {
//        Page<Product> products;
//        if(search == null || search.isEmpty()) {
//            products =  productRepository.findAll(pageable);
//        }else{
//            products = productRepository.findProductByNameContainsIgnoreCase(search, pageable);
//        }
//        return products;
//    }
    @Override
    public Page<Product> findAll(Pageable pageable, String search, Double minPrice, Double maxPrice, String color, String sortOption) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id"); // Default sort by "id"

        // Xử lý giá trị của sortOption
        if ("aToZ".equals(sortOption)) {
            sort = Sort.by(Sort.Direction.ASC, "name");
        } else if ("zToA".equals(sortOption)) {
            sort = Sort.by(Sort.Direction.DESC, "name");
        }
//        else if ("lowToHigh".equals(sortOption)) {
//            sort = Sort.by(Sort.Direction.ASC, "price");
//        } else if ("highToLow".equals(sortOption)) {
//            sort = Sort.by(Sort.Direction.DESC, "price");
//        }

        // Cập nhật lại Pageable với sort mới
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        // Kiểm tra các filter khác (search, minPrice, maxPrice, color)
        if ((search == null || search.isEmpty()) && minPrice == null && maxPrice == null && (color == null || color.isEmpty())) {
            return productRepository.findAll(pageable); // Nếu không có filter, lấy tất cả
        }

        System.out.println("search = " + search);
        System.out.println("minPrice = " + minPrice);
        System.out.println("maxPrice = " + maxPrice);
        System.out.println("color = " + color);
        System.out.println("sortOption = " + sortOption);

        // Gọi repository với các filter
        return productRepository.findProductsWithFilters(pageable, search, minPrice, maxPrice, color,sortOption);
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
     * @update set sale by product request
     * update: konta (3/10.2024)
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
                .sale(productRequest.getSale())
                .build();
        return productRepository.save(product);
    }

    /**
     * @Param id Long
     * @Param productRequest ProductRequest
     * @apiNote Sửa thông tin của sản phẩm dựa theo id sản phẩm
     * @throws DataExistException sản phẩm đã tồn tại
     * @Update setSell by product sell
     * @Update set Sale by productRequest
     * update: konta (3/10.2024)
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
//        product.setSell(product.getSell());
        product.setCategory(categoryService.getCategoryById(productRequest.getCategoryId()));
        product.setSale(productRequest.getSale());
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

    /**
     * @Param id Long
     * @apiNote thay đổi trạng thái sản phẩm
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public Product changeStatus(Long id) {
        Product product = getProductById(id);
        product.setStatus(!product.getStatus());
        return productRepository.save(product);
    }
}
