package com.sh.trippy.domain.reply.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.trippy.domain.reply.api.dto.res.ReplyInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sh.trippy.domain.reply.domain.model.QReply.reply;


@Repository
@RequiredArgsConstructor
public class ReplyQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<List<ReplyInfoDto>> getTripReplyList(Long tripId){

        return Optional.ofNullable(

                jpaQueryFactory.select(Projections.bean(ReplyInfoDto.class,
                                reply.replyId, reply.content, reply.users.profileImg, reply.users.nickname.as("nickname")))
                        .from(reply)
                        .where(reply.trip.tripId.eq(tripId))
                        .join(reply.users)
                        .orderBy(reply.createdAt.asc())
                        .fetch()
        );

    }


}
