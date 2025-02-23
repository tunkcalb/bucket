package com.bucket.store.model.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brandName;

    @Column
    private Integer shippingCharge;

    @Builder
    public Brand(String brandName, Integer shippingCharge) {
        this.brandName = brandName;
        this.shippingCharge = shippingCharge;
    }
}
