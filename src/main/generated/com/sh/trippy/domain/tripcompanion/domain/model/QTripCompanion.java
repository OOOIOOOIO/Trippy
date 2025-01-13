package com.sh.trippy.domain.tripcompanion.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTripCompanion is a Querydsl query type for TripCompanion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTripCompanion extends EntityPathBase<TripCompanion> {

    private static final long serialVersionUID = 1336311381L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTripCompanion tripCompanion = new QTripCompanion("tripCompanion");

    public final com.sh.trippy.domain.common.QBaseTimeEntity _super = new com.sh.trippy.domain.common.QBaseTimeEntity(this);

    public final NumberPath<Long> companionId = createNumber("companionId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.sh.trippy.domain.trip.domain.model.QTrip trip;

    public final EnumPath<TripRole> tripRole = createEnum("tripRole", TripRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sh.trippy.domain.user.domain.QUsers users;

    public QTripCompanion(String variable) {
        this(TripCompanion.class, forVariable(variable), INITS);
    }

    public QTripCompanion(Path<? extends TripCompanion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTripCompanion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTripCompanion(PathMetadata metadata, PathInits inits) {
        this(TripCompanion.class, metadata, inits);
    }

    public QTripCompanion(Class<? extends TripCompanion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trip = inits.isInitialized("trip") ? new com.sh.trippy.domain.trip.domain.model.QTrip(forProperty("trip"), inits.get("trip")) : null;
        this.users = inits.isInitialized("users") ? new com.sh.trippy.domain.user.domain.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

