package com.bucket.store.dto.product;

import lombok.Data;

public class RegistBrandDto {

    @Data
    public static class Request {
        String brandName;
        Integer shippingCharge;
    }
}
