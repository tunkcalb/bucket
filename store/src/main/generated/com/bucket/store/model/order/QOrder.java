package com.bucket.store.model.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -299730043L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final StringPath deliveryAddress = createString("deliveryAddress");

    public final NumberPath<Integer> discountAmount = createNumber("discountAmount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> orderAmount = createNumber("orderAmount", Integer.class);

    public final DatePath<java.time.LocalDate> orderDate = createDate("orderDate", java.time.LocalDate.class);

    public final StringPath orderName = createString("orderName");

    public final StringPath orderStatus = createString("orderStatus");

    public final NumberPath<Integer> shippingCharge = createNumber("shippingCharge", Integer.class);

    public final com.bucket.store.model.user.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.bucket.store.model.user.QUser(forProperty("user")) : null;
    }

}

