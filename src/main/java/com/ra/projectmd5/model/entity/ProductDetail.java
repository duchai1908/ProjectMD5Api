package com.ra.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="productdetail_name",nullable = false,unique = true, length = 100)
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date created_at;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date updated_at;
    @ManyToOne
    @JoinColumn(name="color_id")
    private Color color;
    @ManyToOne
    @JoinColumn(name="size_id")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
