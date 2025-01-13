package com.sh.trippy.domain.stuff.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStuff is a Querydsl query type for Stuff
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStuff extends EntityPathBase<Stuff> {

    private static final long serialVersionUID = 1298606421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStuff stuff1 = new QStuff("stuff1");

    public final com.sh.trippy.domain.common.QBaseTimeEntity _super = new com.sh.trippy.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.sh.trippy.domain.sharechecklist.domain.model.QShareChecklist shareChecklist;

    public final StringPath stuff = createString("stuff");

    public final NumberPath<Long> stuffId = createNumber("stuffId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sh.trippy.domain.user.domain.QUsers users;

    public QStuff(String variable) {
        this(Stuff.class, forVariable(variable), INITS);
    }

    public QStuff(Path<? extends Stuff> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStuff(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStuff(PathMetadata metadata, PathInits inits) {
        this(Stuff.class, metadata, inits);
    }

    public QStuff(Class<? extends Stuff> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shareChecklist = inits.isInitialized("shareChecklist") ? new com.sh.trippy.domain.sharechecklist.domain.model.QShareChecklist(forProperty("shareChecklist"), inits.get("shareChecklist")) : null;
        this.users = inits.isInitialized("users") ? new com.sh.trippy.domain.user.domain.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

