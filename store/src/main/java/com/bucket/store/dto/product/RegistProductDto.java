package com.bucket.store.dto.product;

import com.bucket.store.model.product.Brand;
import com.bucket.store.model.product.Product;
import lombok.Data;

public class RegistProductDto {

    @Data
    public static class Request {
        private String productCode;
        private Integer productStock;
        private String productName;
        private String productOption;
        private String productSize;
        private Long brandId;

        public Product toProductEntity(Brand brand) {
            Product product = Product.builder()
                    .productCode(productCode)
                    .productStock(productStock)
                    .productName(productName)
                    .productOption(productOption)
                    .productSize(productSize)
                    .brand(brand).build();
            return product;
        }
    }
}
