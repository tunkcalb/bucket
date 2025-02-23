package com.bucket.store.service.product;

import com.bucket.store.dto.product.RegistProductDto;
import com.bucket.store.exception.CustomException;
import com.bucket.store.exception.error.ErrorCode;
import com.bucket.store.model.product.Brand;
import com.bucket.store.model.product.Product;
import com.bucket.store.repository.product.BrandRepository;
import com.bucket.store.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public void registProduct(RegistProductDto.Request request) {
        Brand brand = brandRepository.findById(request.getBrandId()).get();
        if (brand == null) {
            throw new CustomException(ErrorCode.BRAND_NOT_FOUND);
        }
        Product product = request.toProductEntity(brand);
        productRepository.save(product);
    }

    public void createProducts(int N, int stock) {
        List<Brand> brands = createBrands();

        List<Product> products = new ArrayList<>();
        Random random = new Random();
        int cnt = 1;

        // 옵션, 사이즈
        List<String> options = Arrays.asList("그레이", "옐로우", "블루", "레드", "그린");
        List<String> sizes = Arrays.asList("S", "M", "L", "XL");

        // 각 브랜드에 최소 1개의 상품 할당
        for (Brand brand : brands) {
            String name = brand.getBrandName() + " Product " + cnt++;
            Product product = Product.builder()
                    .productName(name)
                    .productCode(name)
                    .productOption(options.get(random.nextInt(options.size()))) // 랜덤 옵션 할당
                    .productSize(sizes.get(random.nextInt(sizes.size()))) // 랜덤 사이즈 할당
                    .amount(10000 + random.nextInt(10001)) // 10000원 ~ 20000원
                    .productStock(stock)
                    .brand(brand)
                    .build();
            products.add(product);
        }

        // 나머지 상품(74개) 랜덤하게 할당
        while (cnt <= N) {
            Brand randomBrand = brands.get(random.nextInt(brands.size())); // 랜덤 브랜드 선택
            String name = randomBrand.getBrandName() + " Product " + cnt++;
            Product product = Product.builder()
                    .productName(name)
                    .productCode(name)
                    .productOption(options.get(random.nextInt(options.size()))) // 랜덤 옵션 할당
                    .productSize(sizes.get(random.nextInt(sizes.size()))) // 랜덤 사이즈 할당
                    .amount(10000 + random.nextInt(10001)) // 10000원 ~ 20000원
                    .productStock(stock)
                    .brand(randomBrand)
                    .build();
            products.add(product);
        }

        // 상품 저장
        productRepository.saveAll(products);
    }

    public List<Brand> createBrands() {

        //브랜드 정보 생성
        List<Brand> brands = new ArrayList<>();
        Random random = new Random();
        Set<Integer> set = new HashSet<>(); // 이미 사용된 배송비 저장

        // A부터 Z까지 브랜드 생성
        for (char c = 'A'; c <= 'Z'; c++) {
            String brandName = String.valueOf(c);
            int shippingCharge;

            // 중복되지 않는 배송비 생성
            do {
                shippingCharge = 1000 + random.nextInt(9001); // 1000원부터 10000원 사이의 랜덤 값
            } while (set.contains(shippingCharge)); // 중복 검사

            set.add(shippingCharge); // 사용된 배송비 저장

            Brand brand = Brand.builder()
                    .brandName(brandName)
                    .shippingCharge(shippingCharge)
                    .build();

            brands.add(brand);
        }

        brandRepository.saveAll(brands);
        return brands;
    }
}
