package com.ra.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties({"password","id"})
    private User user;
    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductDetail productDetail;
}
