package com.sh.trippy.domain.tripstats.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTripStats is a Querydsl query type for TripStats
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTripStats extends EntityPathBase<TripStats> {

    private static final long serialVersionUID = 1149008629L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTripStats tripStats = new QTripStats("tripStats");

    public final NumberPath<Integer> abroadCnt = createNumber("abroadCnt", Integer.class);

    public final NumberPath<Integer> domesticCnt = createNumber("domesticCnt", Integer.class);

    public final NumberPath<Long> statsId = createNumber("statsId", Long.class);

    public final com.sh.trippy.domain.user.domain.QUsers users;

    public QTripStats(String variable) {
        this(TripStats.class, forVariable(variable), INITS);
    }

    public QTripStats(Path<? extends TripStats> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTripStats(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTripStats(PathMetadata metadata, PathInits inits) {
        this(TripStats.class, metadata, inits);
    }

    public QTripStats(Class<? extends TripStats> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.sh.trippy.domain.user.domain.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

