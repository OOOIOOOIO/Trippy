package com.sh.trippy.domain.reply.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.reply.api.dto.req.ReplyCreateReqDto;
import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip")
    private Trip trip;

    @Builder
    private Reply(String content, Users users, Trip trip) {
        this.content = content;
        this.users = users;
        this.trip = trip;
    }

    public static Reply createReply(ReplyCreateReqDto replyCreateReqDto, Trip trip, Users users){
        return Reply.builder()
                .content(replyCreateReqDto.getContent())
                .trip(trip)
                .users(users)
                .build();
    }


}
