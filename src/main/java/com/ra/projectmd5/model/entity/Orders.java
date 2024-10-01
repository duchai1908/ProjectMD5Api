package com.ra.projectmd5.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ra.projectmd5.constants.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String note;
    @NotBlank(message = "Receive name must not be null")
    private String receiveName;
    @NotBlank(message = "Receive address must not be null")
    private String receiveAddress;
    @NotBlank(message = "Receive phone must not be null")
    private String receivePhone;
    @ManyToOne
    @JoinColumn(name="coupon_id")
    private Coupon coupon;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date receivedAt;
}
