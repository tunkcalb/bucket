package com.bucket.store.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProductDto {
    Long productId;
    Integer amount;
}
