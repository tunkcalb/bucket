package com.bucket.store.model.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column
    private String productCode;

    @Column
    private Integer productStock;

    @Column
    private String productName;

    @Column
    private String productOption;

    @Column
    private String productSize;

    @ManyToOne
    private Brand brand;

    @Builder
    public Product(Long id, String productCode, Integer productStock, String productName, String productOption, String productSize, Brand brand) {
        this.id = id;
        this.productCode = productCode;
        this.productStock = productStock;
        this.productName = productName;
        this.productOption = productOption;
        this.productSize = productSize;
        this.brand = brand;
    }
}
