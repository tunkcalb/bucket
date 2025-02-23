package com.bucket.store.model.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserLog is a Querydsl query type for UserLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserLog extends EntityPathBase<UserLog> {

    private static final long serialVersionUID = -922813409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLog userLog = new QUserLog("userLog");

    public final DateTimePath<java.time.LocalDateTime> accessTimestamp = createDateTime("accessTimestamp", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath page = createString("page");

    public final QUser user;

    public QUserLog(String variable) {
        this(UserLog.class, forVariable(variable), INITS);
    }

    public QUserLog(Path<? extends UserLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserLog(PathMetadata metadata, PathInits inits) {
        this(UserLog.class, metadata, inits);
    }

    public QUserLog(Class<? extends UserLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

