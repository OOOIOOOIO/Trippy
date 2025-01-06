package com.sh.trippy.domain.feedback.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fbId;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private Users users;
}
