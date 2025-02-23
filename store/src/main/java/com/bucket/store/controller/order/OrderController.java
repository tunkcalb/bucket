package com.bucket.store.controller.order;

import com.bucket.store.dto.Response;
import com.bucket.store.dto.order.CreateOrderDto;
import com.bucket.store.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public Response createOrder(@RequestBody CreateOrderDto.Request request) {
        orderService.createOrder(request);
        return new Response(201, "주문 완료");
    }
}
