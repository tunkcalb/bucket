package com.bucket.store.model.order;

import com.bucket.store.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id")
    private Long id;

    @Column
    private String orderName;

    @Column
    private String deliveryAddress;

    @Column
    private Integer shippingCharge;

    @Column
    private String orderStatus;

    @Column
    private Integer orderAmount;

    @Column
    private Integer discountAmount;

    @ManyToOne
    private User user;

    @Builder
    public Order(Long id, String orderName, String deliveryAddress, Integer shippingCharge, String orderStatus, Integer orderAmount, Integer discountAmount) {
        this.id = id;
        this.orderName = orderName;
        this.deliveryAddress = deliveryAddress;
        this.shippingCharge = shippingCharge;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.discountAmount = discountAmount;
    }
}
