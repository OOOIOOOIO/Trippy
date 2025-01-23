package com.sh.trippy.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -723235893L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsers users = new QUsers("users");

    public final com.sh.trippy.domain.common.QBaseTimeEntity _super = new com.sh.trippy.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final ListPath<com.sh.trippy.domain.feedback.domain.model.Feedback, com.sh.trippy.domain.feedback.domain.model.QFeedback> feedbackList = this.<com.sh.trippy.domain.feedback.domain.model.Feedback, com.sh.trippy.domain.feedback.domain.model.QFeedback>createList("feedbackList", com.sh.trippy.domain.feedback.domain.model.Feedback.class, com.sh.trippy.domain.feedback.domain.model.QFeedback.class, PathInits.DIRECT2);

    public final StringPath motherLand = createString("motherLand");

    public final StringPath nickname = createString("nickname");

    public final BooleanPath paidFlag = createBoolean("paidFlag");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath provider = createString("provider");

    public final DatePath<java.time.LocalDate> purchasedAt = createDate("purchasedAt", java.time.LocalDate.class);

    public final StringPath refreshToken = createString("refreshToken");

    public final ListPath<com.sh.trippy.domain.reply.domain.model.Reply, com.sh.trippy.domain.reply.domain.model.QReply> replyList = this.<com.sh.trippy.domain.reply.domain.model.Reply, com.sh.trippy.domain.reply.domain.model.QReply>createList("replyList", com.sh.trippy.domain.reply.domain.model.Reply.class, com.sh.trippy.domain.reply.domain.model.QReply.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion, com.sh.trippy.domain.tripcompanion.domain.model.QTripCompanion> tripCompanionList = this.<com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion, com.sh.trippy.domain.tripcompanion.domain.model.QTripCompanion>createList("tripCompanionList", com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion.class, com.sh.trippy.domain.tripcompanion.domain.model.QTripCompanion.class, PathInits.DIRECT2);

    public final ListPath<com.sh.trippy.domain.trip.domain.model.Trip, com.sh.trippy.domain.trip.domain.model.QTrip> tripList = this.<com.sh.trippy.domain.trip.domain.model.Trip, com.sh.trippy.domain.trip.domain.model.QTrip>createList("tripList", com.sh.trippy.domain.trip.domain.model.Trip.class, com.sh.trippy.domain.trip.domain.model.QTrip.class, PathInits.DIRECT2);

    public final com.sh.trippy.domain.tripstats.domain.model.QTripStats tripStats;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUsers(String variable) {
        this(Users.class, forVariable(variable), INITS);
    }

    public QUsers(Path<? extends Users> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsers(PathMetadata metadata, PathInits inits) {
        this(Users.class, metadata, inits);
    }

    public QUsers(Class<? extends Users> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tripStats = inits.isInitialized("tripStats") ? new com.sh.trippy.domain.tripstats.domain.model.QTripStats(forProperty("tripStats"), inits.get("tripStats")) : null;
    }

}

