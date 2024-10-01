package com.ra.projectmd5.model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductDetail productDetail;
    @Column(name="product_name")
    private String productName;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Orders orders;
    @Column(name="quantity",nullable = false)
    private Integer quantity;
}
