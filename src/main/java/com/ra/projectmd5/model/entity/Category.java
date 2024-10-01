package com.ra.projectmd5.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="category_name",nullable = false,unique = true, length = 50)
    private String name;
    private String description;
    private Boolean status;
    private String image;
}
