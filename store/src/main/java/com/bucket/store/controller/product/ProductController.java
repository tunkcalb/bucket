package com.bucket.store.controller.product;

import com.bucket.store.dto.Response;
import com.bucket.store.dto.product.RegistProductDto;
import com.bucket.store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public Response registProduct(@RequestBody RegistProductDto.Request request) {
        productService.registProduct(request);
        return new Response(201, "상품 등록 완료");
    }
}
