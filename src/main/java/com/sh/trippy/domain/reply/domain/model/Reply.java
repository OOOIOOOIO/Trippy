package com.sh.trippy.domain.reply.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @JoinColumn(name = "users")
    private Trip trip;
}
