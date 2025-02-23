package com.bucket.store.model.order;

import com.bucket.store.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Setter
    @Column(name = "order_date") // 주문일자 필드 추가
    private LocalDate orderDate;

    @ManyToOne
    private User user;

    @Builder
    public Order(Long id, String orderName, String deliveryAddress, Integer shippingCharge, String orderStatus, Integer orderAmount, Integer discountAmount, LocalDate orderDate, User user) {
        this.id = id;
        this.orderName = orderName;
        this.deliveryAddress = deliveryAddress;
        this.shippingCharge = shippingCharge;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.discountAmount = discountAmount;
        this.orderDate = orderDate;
        this.user = user;
    }
}
