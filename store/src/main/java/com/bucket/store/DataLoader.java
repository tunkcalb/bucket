package com.bucket.store;

import com.bucket.store.service.order.OrderService;
import com.bucket.store.service.product.BrandService;
import com.bucket.store.service.product.ProductService;
import com.bucket.store.service.user.UserLogService;
import com.bucket.store.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserLogService userLogService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public DataLoader(UserLogService userLogService, UserService userService, ProductService productService, OrderService orderService) {
        this.userLogService = userLogService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        // static 폴더에 있는 JSON 파일 경로
        String filePath = "static/sample_user_log.json";

        // 2-a 유저 정보 저장
        userService.saveUserFromJson(filePath);

        // 2-b 상품 정보 생성
        productService.createProducts(100, 1000);

        // 2-c 주문 정보 생성
        orderService.createOrders(1000);

        // 2-d 유저 로그 저장
        userLogService.saveUserLogsFromJson(filePath);
    }
}