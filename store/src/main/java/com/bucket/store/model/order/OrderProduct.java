package com.bucket.store.model.order;

import com.bucket.store.model.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_product_id")
    private Long id;

    @Column
    private Integer orderQuantity;

    @ManyToOne
    private Product product;

    @Setter
    @ManyToOne
    private Order order;

    @Builder
    public OrderProduct(Long id, Integer orderQuantity, Product product, Order order) {
        this.id = id;
        this.orderQuantity = orderQuantity;
        this.product = product;
        this.order = order;
    }
}
