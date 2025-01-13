package com.sh.trippy.domain.plan.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlan is a Querydsl query type for Plan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlan extends EntityPathBase<Plan> {

    private static final long serialVersionUID = -1770448183L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlan plan = new QPlan("plan");

    public final com.sh.trippy.domain.common.QBaseTimeEntity _super = new com.sh.trippy.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath memo = createString("memo");

    public final StringPath place = createString("place");

    public final NumberPath<Long> planId = createNumber("planId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> plannedAt = createDateTime("plannedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public final StringPath title = createString("title");

    public final com.sh.trippy.domain.trip.domain.model.QTrip trip;

    public final DatePath<java.time.LocalDate> tripDate = createDate("tripDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPlan(String variable) {
        this(Plan.class, forVariable(variable), INITS);
    }

    public QPlan(Path<? extends Plan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlan(PathMetadata metadata, PathInits inits) {
        this(Plan.class, metadata, inits);
    }

    public QPlan(Class<? extends Plan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trip = inits.isInitialized("trip") ? new com.sh.trippy.domain.trip.domain.model.QTrip(forProperty("trip"), inits.get("trip")) : null;
    }

}

