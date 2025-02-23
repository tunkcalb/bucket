package com.bucket.store.service.product;

import com.bucket.store.dto.product.RegistBrandDto;
import com.bucket.store.model.product.Brand;
import com.bucket.store.repository.product.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public void registBrand(RegistBrandDto.Request request) {
        String brandName = request.getBrandName();
        Integer shippingCharge = request.getShippingCharge();
        Brand brand = Brand.builder()
                .brandName(brandName)
                .shippingCharge(shippingCharge).build();
        brandRepository.save(brand);
    }


}
