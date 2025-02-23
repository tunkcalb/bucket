package com.bucket.store.dto.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class CreateOrderDto {

    @Data
    @Builder
    public static class Request {
        String orderName;

        String deliveryAddress;

        String orderStatus;
        
        Integer discountAmount;

        LocalDate orderDate;

        List<OrderProductDto> orderProductDtoList;

        String userId;
    }
}
