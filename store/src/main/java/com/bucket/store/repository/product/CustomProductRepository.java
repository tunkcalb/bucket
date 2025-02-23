package com.bucket.store.repository.product;

import com.bucket.store.model.product.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.bucket.store.model.product.QBrand.brand;
import static com.bucket.store.model.product.QProduct.product;

@Repository
public class CustomProductRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CustomProductRepository(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    public Product findByProductId(Long productId) {
        return queryFactory
                .selectFrom(product)
                .leftJoin(product.brand, brand)
                .where(product.id.eq(productId))
                .fetchOne();
    }
}
