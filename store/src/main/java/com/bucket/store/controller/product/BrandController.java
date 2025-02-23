package com.bucket.store.controller.product;

import com.bucket.store.dto.Response;
import com.bucket.store.dto.product.RegistBrandDto;
import com.bucket.store.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public Response registBrand(@RequestBody RegistBrandDto.Request request) {
        brandService.registBrand(request);
        return new Response(201, "브랜드 등록 완료.");
    }
}
