package com.bucket.store.service.product;

import com.bucket.store.repository.product.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
}
