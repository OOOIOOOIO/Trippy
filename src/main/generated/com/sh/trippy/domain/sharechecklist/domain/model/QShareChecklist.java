package com.sh.trippy.domain.sharechecklist.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShareChecklist is a Querydsl query type for ShareChecklist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShareChecklist extends EntityPathBase<ShareChecklist> {

    private static final long serialVersionUID = -1552461531L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShareChecklist shareChecklist = new QShareChecklist("shareChecklist");

    public final com.sh.trippy.domain.common.QBaseTimeEntity _super = new com.sh.trippy.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> shId = createNumber("shId", Long.class);

    public final ListPath<com.sh.trippy.domain.stuff.domain.model.Stuff, com.sh.trippy.domain.stuff.domain.model.QStuff> stuffList = this.<com.sh.trippy.domain.stuff.domain.model.Stuff, com.sh.trippy.domain.stuff.domain.model.QStuff>createList("stuffList", com.sh.trippy.domain.stuff.domain.model.Stuff.class, com.sh.trippy.domain.stuff.domain.model.QStuff.class, PathInits.DIRECT2);

    public final com.sh.trippy.domain.trip.domain.model.QTrip trip;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShareChecklist(String variable) {
        this(ShareChecklist.class, forVariable(variable), INITS);
    }

    public QShareChecklist(Path<? extends ShareChecklist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShareChecklist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShareChecklist(PathMetadata metadata, PathInits inits) {
        this(ShareChecklist.class, metadata, inits);
    }

    public QShareChecklist(Class<? extends ShareChecklist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trip = inits.isInitialized("trip") ? new com.sh.trippy.domain.trip.domain.model.QTrip(forProperty("trip"), inits.get("trip")) : null;
    }

}

