package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.constants.Status;
import com.ra.projectmd5.model.dto.request.OrderRequest;
import com.ra.projectmd5.model.dto.response.MonthlyRevenueResponse;
import com.ra.projectmd5.model.dto.response.OrdersResponse;
import com.ra.projectmd5.model.entity.*;
import com.ra.projectmd5.model.repository.IOrderDetailRepository;
import com.ra.projectmd5.model.repository.IOrdersRepository;
import com.ra.projectmd5.model.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements IOrdersService {
    private final IOrdersRepository ordersRepository;
    private final ICouponService couponService;
    private final IAddressService addressService;
    private final IUserService userService;
    private final ICartItemService cartItemService;
    private final UUIDService uuidService;
    private final IOrderDetailRepository orderDetailRepository;
    private final IProductService productService;
    private final IProductDetailService productDetailService;

    /**
     * @Param pageable Pageable
     * @Param search String
     * @Param userId Long
     * @apiNote Lấy tất cả orders theo user đang đăng nhập có phân trang tìm kiếm
     * @Auth Duc Hai (05/10/2024)
     * */
    @Override
    public Page<Orders> findAllOrdersByUser(Pageable pageable, String search,Long userId) {
        Page<Orders> ordersPage;
        if(search != null && !search.isEmpty()) {
            ordersPage = ordersRepository.getAllOrderByUser(userId,search,pageable);

        }else{
            ordersPage = ordersRepository.findAllByUserId(userId,pageable);
        }
        return ordersPage;
    }

    /**
     * @Param orderRequest OrderRequest
     * @Param userId Long
     * @apiNote Tạo đơn hàng mới
     * @throws NoSuchElementException giỏ hàng trống
     * @Auth Duc Hai (05/10/2024)
     * */
    @Override
    public Orders addToOrders(OrderRequest orderRequest, Long userId) {
        Address address = addressService.getAddress(orderRequest.getAddressId());
        User user = userService.findById(userId);
        Coupon coupon = null;
        // Kiểm tra giỏ hàng có sản phẩm không
        List<CartItem> cartItemList = cartItemService.getAllCartItemsNoPagination(userId);
        if(cartItemList.isEmpty()) {
            throw new NoSuchElementException("Giỏ hàng trống");
        }

        // Tổng tiền đơn hàng
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItemList) {
            totalPrice += cartItem.getQuantity() * cartItem.getProductDetail().getPrice();
        }
        if(orderRequest.getCouponId() != null){
            coupon = couponService.getCoupon(orderRequest.getCouponId());
            totalPrice = totalPrice - (totalPrice * coupon.getPercent() / 100);
        }

        // Ngày tạo đơn hàng và ngày dự kiến giao hàng
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        Date futureDate = calendar.getTime();

        // Tạo giỏ hàng
        Orders orders = Orders.builder()
                .code(uuidService.couponUUID())
                .user(user)
                .note(orderRequest.getNote())
                .coupon(coupon)
                .status(Status.WAITING)
                .totalPrice(totalPrice)
                .receivePhone(address.getPhone())
                .receiveName(address.getReceiveName())
                .receiveAddress(address.getAddress())
                .createdAt(currentDate)
                .receivedAt(futureDate)
                .build();
        orders = ordersRepository.save(orders);

        // Tạo chi tiết đơn hàng
        for (CartItem cartItem : cartItemList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setProductDetail(cartItem.getProductDetail());
            orderDetail.setOrders(orders);
            orderDetail.setProductName(cartItem.getProductDetail().getProduct().getName());
            orderDetailRepository.save(orderDetail);
            ProductDetail productDetail = cartItem.getProductDetail();
            productDetail.setStock(productDetail.getStock() - cartItem.getQuantity());
            productDetailService.save(productDetail);
            Product product = productDetail.getProduct();
            product.setSell(product.getSell() + cartItem.getQuantity());
            productService.save(product);
        }

        // Xoá giỏ hàng
        cartItemService.deleteAllCartItems(userId);

        return orders;
    }
    /**
     * @Param id Long
     * @Param userId Long
     * @apiNote Lấy đơn hàng thông qua Id
     * @throws NoSuchElementException Không tìm thấy đơn hàng
     * @Auth Duc Hai (05/10/2024)
     * */
    @Override
    public OrdersResponse getOrdersById(Long id, Long userId) {
        Orders orders = ordersRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Không tìm thấy đơn hàng"));
        if(!Objects.equals(orders.getUser().getId(), userId)){
            throw new NoSuchElementException("Không tìm thấy đơn hàng");
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findOrderDetailsByOrdersId(id);
        OrdersResponse ordersResponse = new OrdersResponse();
        ordersResponse.setOrderDetail(orderDetailList);
        ordersResponse.setOrders(orders);
        return ordersResponse;
    }

    /**
     * @Param ordersId Long
     * @Param status String
     * @apiNote Thay đổi trạng thái đơn hàng
     * @throws NoSuchElementException Không tìm thấy đơn hàng
     * @Auth Duc Hai (05/10/2024)
     * */
    @Override
    public Orders changeStatus(Long ordersId, String status) {
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(()-> new NoSuchElementException("Không tìm thấy đơn hàng"));
        Status s;
        try {
            s = Status.valueOf(status);
        }catch (Exception e){
            throw new NoSuchElementException("Không tìm thấy trạng thái");
        }
        orders.setStatus(s);
        return ordersRepository.save(orders);
    }

    /**
     * @Param ordersId Long
     * @Param userId Long
     * @apiNote Huỷ đơn hàng ở trạng thái WAITTING
     * @throws BadRequestException Không thể huỷ đơn hàng
     * @Auth Duc Hai (05/10/2024)
     * */
    @Override
    public Orders cancelOrder(Long ordersId, Long userId) throws BadRequestException {
        Orders orders = ordersRepository.findByUserIdAndId(userId,ordersId).orElseThrow(()->new NoSuchElementException("Không tìm thấy đơn hàng"));
        if(orders.getStatus() == Status.WAITING){
            orders.setStatus(Status.CANCEL);
            ordersRepository.save(orders);
        }else{
            throw new BadRequestException("Không thể huỷ đơn hàng");
        }
        return orders;
    }

    @Override
    public List<MonthlyRevenueResponse> getAllRevenue() {
        return ordersRepository.findMonthlyRevenue();
    }

    @Override
    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }
}
