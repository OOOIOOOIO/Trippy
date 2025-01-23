package com.sh.trippy.domain.trip.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrip is a Querydsl query type for Trip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrip extends EntityPathBase<Trip> {

    private static final long serialVersionUID = -729078335L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrip trip = new QTrip("trip");

    public final com.sh.trippy.domain.common.QBaseTimeEntity _super = new com.sh.trippy.domain.common.QBaseTimeEntity(this);

    public final BooleanPath abroadFlag = createBoolean("abroadFlag");

    public final DatePath<java.time.LocalDate> arrivalDate = createDate("arrivalDate", java.time.LocalDate.class);

    public final BooleanPath beenFlag = createBoolean("beenFlag");

    public final StringPath country = createString("country");

    public final StringPath countryEmogi = createString("countryEmogi");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> departureDate = createDate("departureDate", java.time.LocalDate.class);

    public final ListPath<com.sh.trippy.domain.plan.domain.model.Plan, com.sh.trippy.domain.plan.domain.model.QPlan> planList = this.<com.sh.trippy.domain.plan.domain.model.Plan, com.sh.trippy.domain.plan.domain.model.QPlan>createList("planList", com.sh.trippy.domain.plan.domain.model.Plan.class, com.sh.trippy.domain.plan.domain.model.QPlan.class, PathInits.DIRECT2);

    public final ListPath<com.sh.trippy.domain.reply.domain.model.Reply, com.sh.trippy.domain.reply.domain.model.QReply> replyList = this.<com.sh.trippy.domain.reply.domain.model.Reply, com.sh.trippy.domain.reply.domain.model.QReply>createList("replyList", com.sh.trippy.domain.reply.domain.model.Reply.class, com.sh.trippy.domain.reply.domain.model.QReply.class, PathInits.DIRECT2);

    public final com.sh.trippy.domain.sharechecklist.domain.model.QShareChecklist shareChecklist;

    public final ListPath<com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion, com.sh.trippy.domain.tripcompanion.domain.model.QTripCompanion> tripCompanionList = this.<com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion, com.sh.trippy.domain.tripcompanion.domain.model.QTripCompanion>createList("tripCompanionList", com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion.class, com.sh.trippy.domain.tripcompanion.domain.model.QTripCompanion.class, PathInits.DIRECT2);

    public final NumberPath<Long> tripId = createNumber("tripId", Long.class);

    public final ListPath<com.sh.trippy.domain.tripinvitaion.domain.model.TripInvitation, com.sh.trippy.domain.tripinvitaion.domain.model.QTripInvitation> tripInvitationList = this.<com.sh.trippy.domain.tripinvitaion.domain.model.TripInvitation, com.sh.trippy.domain.tripinvitaion.domain.model.QTripInvitation>createList("tripInvitationList", com.sh.trippy.domain.tripinvitaion.domain.model.TripInvitation.class, com.sh.trippy.domain.tripinvitaion.domain.model.QTripInvitation.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sh.trippy.domain.user.domain.QUsers users;

    public QTrip(String variable) {
        this(Trip.class, forVariable(variable), INITS);
    }

    public QTrip(Path<? extends Trip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrip(PathMetadata metadata, PathInits inits) {
        this(Trip.class, metadata, inits);
    }

    public QTrip(Class<? extends Trip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shareChecklist = inits.isInitialized("shareChecklist") ? new com.sh.trippy.domain.sharechecklist.domain.model.QShareChecklist(forProperty("shareChecklist"), inits.get("shareChecklist")) : null;
        this.users = inits.isInitialized("users") ? new com.sh.trippy.domain.user.domain.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

