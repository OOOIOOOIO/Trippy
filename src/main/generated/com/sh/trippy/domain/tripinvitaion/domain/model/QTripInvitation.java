package com.sh.trippy.domain.tripinvitaion.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTripInvitation is a Querydsl query type for TripInvitation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTripInvitation extends EntityPathBase<TripInvitation> {

    private static final long serialVersionUID = 2130057415L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTripInvitation tripInvitation = new QTripInvitation("tripInvitation");

    public final com.sh.trippy.domain.trip.domain.model.QTrip trip;

    public final NumberPath<Long> tripInvitationId = createNumber("tripInvitationId", Long.class);

    public final NumberPath<Long> userFrom = createNumber("userFrom", Long.class);

    public final NumberPath<Long> userTo = createNumber("userTo", Long.class);

    public QTripInvitation(String variable) {
        this(TripInvitation.class, forVariable(variable), INITS);
    }

    public QTripInvitation(Path<? extends TripInvitation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTripInvitation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTripInvitation(PathMetadata metadata, PathInits inits) {
        this(TripInvitation.class, metadata, inits);
    }

    public QTripInvitation(Class<? extends TripInvitation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trip = inits.isInitialized("trip") ? new com.sh.trippy.domain.trip.domain.model.QTrip(forProperty("trip"), inits.get("trip")) : null;
    }

}

