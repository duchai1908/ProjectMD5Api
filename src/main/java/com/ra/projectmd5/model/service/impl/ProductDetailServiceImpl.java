package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.ProductDetailRequest;
import com.ra.projectmd5.model.entity.*;
import com.ra.projectmd5.model.repository.IProductDetailRepository;
import com.ra.projectmd5.model.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements IProductDetailService {
    private final IProductService productService;
    private final IProductDetailRepository productDetailRepository;
    private final IImageProductDetailService imageProductDetailService;
    private final ISizeService sizeService;
    private final IColorService colorService;
    private final UploadService uploadService;

    /**
     * @Param pageable Pageable
     * @Param search String
     * @Param id Long
     * @apiNote tìm kiếm tất cả ProductDetail theo ProductId ( có phân trang, sắp xếp, tìm kiếm)
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public Page<ProductDetail> findAllProductDetail(Pageable pageable, String search, Long id) {
        productService.getProductById(id);
        Page<ProductDetail> productDetails;
        if(search == null || search.equals("")) {
            productDetails = productDetailRepository.findAllByProductId(id,pageable);
        }else{
            productDetails = productDetailRepository.findAllByProductIdContainsIgnoreCase(id,search,pageable);
        }
        return productDetails;
    }

    /**
     * @Param id Long
     * @apiNote Tìm kiếm ProductDetail theo productDetail id
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public ProductDetail getProductDetailById(Long id) {
        return productDetailRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Không tìm tấy chi tiết sản phẩm có id là: "+id));
    }

    /**
     * @Param productDetailRequest ProductDetailRequest
     * @apiNote Thêm mới ProductDetail
     * @throws DataExistException Tên ProductDetail bị trùng lặp
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public ProductDetail saveProductDetail(ProductDetailRequest productDetailRequest) throws DataExistException {
        productService.getProductById(productDetailRequest.getProductId());
        sizeService.getSizeById(productDetailRequest.getSizeId());
        colorService.getColorById(productDetailRequest.getColorId());
//        if(productDetailRepository.existsByName(productDetailRequest.getName())){
//            throw new DataExistException("Tên chi tiết sản phẩm đã tồn tại","name");
//        }
        ProductDetail productDetail = ProductDetail.builder()
                .name(productDetailRequest.getName())
                .description(productDetailRequest.getDescription())
                .price(productDetailRequest.getPrice())
                .stock(productDetailRequest.getStock())
                .color(colorService.getColorById(productDetailRequest.getColorId()))
                .size(sizeService.getSizeById(productDetailRequest.getSizeId()))
                .product(productService.getProductById(productDetailRequest.getProductId()))
                .created_at(new Date())
                .updated_at(new Date())
                .build();
        productDetail = productDetailRepository.save(productDetail);
        if(!productDetailRequest.getImages().isEmpty()){
            for(MultipartFile file : productDetailRequest.getImages()){
                ImageProductDetail imageProductDetail = ImageProductDetail.builder()
                        .productDetail(productDetail)
                        .image(uploadService.uploadFileToServer(file))
                        .build();
                imageProductDetailService.save(imageProductDetail);
            }
        }
        return productDetail;
    }

    /**
     * @Param name String
     * @apiNote Kiểm tra tên ProductDetail có tồn tại hay không
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public boolean existsByName(String name) {
        return productDetailRepository.existsByName(name);
    }

    /**
     * @Param productDetailRequest ProductDetailRequest
     * @Param id Long
     * @apiNote Sửa ProductDetail theo ProductDetail id
     * @throws DataExistException tên ProductDetail đã tồn tại
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public ProductDetail updateProductDetail(ProductDetailRequest productDetailRequest, Long id) throws DataExistException {
        productService.getProductById(productDetailRequest.getProductId());
        sizeService.getSizeById(productDetailRequest.getSizeId());
        colorService.getColorById(productDetailRequest.getColorId());
        ProductDetail productDetail = getProductDetailById(id);
        if(!Objects.equals(productDetailRequest.getName(), productDetail.getName()) && existsByName(productDetailRequest.getName())){
            throw new DataExistException("Tên sản phẩm đã tồn tại","name");
        }
        if(!Objects.equals(productDetailRequest.getDescription(), "")){
            productDetail.setDescription(productDetailRequest.getDescription());
        }
        productDetail.setName(productDetailRequest.getName());
        productDetail.setPrice(productDetailRequest.getPrice());
        productDetail.setStock(productDetailRequest.getStock());
        productDetail.setColor(colorService.getColorById(productDetailRequest.getColorId()));
        productDetail.setSize(sizeService.getSizeById(productDetailRequest.getSizeId()));
        productDetail.setProduct(productService.getProductById(productDetailRequest.getProductId()));
        productDetail.setUpdated_at(new Date());
        productDetail = productDetailRepository.save(productDetail);
        if(!productDetailRequest.getImages().isEmpty()){
            imageProductDetailService.deleteImageByProductDetailId(id);
            for(MultipartFile file : productDetailRequest.getImages()){
                ImageProductDetail imageProductDetail = ImageProductDetail.builder()
                        .productDetail(productDetail)
                        .image(uploadService.uploadFileToServer(file))
                        .build();
                imageProductDetailService.save(imageProductDetail);
            }
        }
        return productDetail;
    }

    /**
     * @Param id Long
     * @apiNote Xoá ProductDetail theo ProductDetail id
     * Auth: Duc Hai (02/10/2024)
     * */
    @Override
    public void deleteProductDetail(Long id) {
        ProductDetail productDetail = getProductDetailById(id);
        imageProductDetailService.deleteImageByProductDetailId(id);
        productDetailRepository.delete(productDetail);
    }


    @Override
    public List<ProductDetail> findAllProductDetailByNothing() {
        return productDetailRepository.findAll();
    }

    @Override
    public List<ProductDetail> findAllProductDetailByProductId(Long id) {
        return productDetailRepository.findProductDetailsByProduct_Id(id);

    /**
     * @Param colorId Long
     * @Param sizeId Long
     * @Param productId Long
     * @apiNote tìm kiếm chi tiết sản phẩm theo color id, size id và product id
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public ProductDetail getProductDetailByColorAndSize(Long colorId, Long sizeId, Long productId) {
        Color color = colorService.getColorById(colorId);
        Size size = sizeService.getSizeById(sizeId);
        Product product = productService.getProductById(productId);
        return productDetailRepository.findByColorAndSizeAndProduct(color, size, product);

    }
}
