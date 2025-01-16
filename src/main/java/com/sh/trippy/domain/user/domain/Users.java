package com.sh.trippy.domain.user.domain;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.feedback.domain.model.Feedback;
import com.sh.trippy.domain.reply.domain.model.Reply;
import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import com.sh.trippy.domain.user.api.dto.UserInfoUpdateReqDto;
import com.sh.trippy.domain.tripstats.domain.model.TripStats;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String email;
    private String profileImg;
    @NotNull
    private String provider;
    private String nickname;
    private String motherLand;
    @NotNull
    private boolean paidFlag;
    private LocalDate purchasedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private TripStats tripStats;

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Feedback> feedbackList = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<TripCompanion> tripCompanionList = new ArrayList<>();



    @Builder
    public Users(String email, String provider, boolean paidFlag, Role role) {
        this.email = email;
        this.provider = provider;
        this.paidFlag = paidFlag;
        this.purchasedAt = null;
        this.role = role;
    }

    public void updateRole(Role roles) {
        this.role = roles;
    }

    /**
     * 양방향 연관관계, cascade 유의
     */
//    public void addBigGoal(BigGoal bigGoal){
//        if(bigGoal.getUsers() != null){
//            bigGoal.getUsers().getBigGoalList().remove(bigGoal);
//        }
//        this.bigGoalList.add(bigGoal);
//        bigGoal.setUsers(this);
//    }
//
//    public void addDelayGoal(DelayRule delayRule){
//        if(delayRule.getUsers() != null){
//            delayRule.getUsers().getBigGoalList().remove(delayRule);
//        }
//        this.delayRuleList.add(delayRule);
//        delayRule.setUsers(this);
//    }

    /**
     * user 생성
     */
    public static Users createUser(String email, String provider, Role role){
        return Users.builder()
                .email(email)
                .provider(provider)
                .paidFlag(false) // 기본 요금제: false
                .role(role)
                .build();
    }

    public Users update(String email, String picture, String provider){
        this.email = email;
        this.provider = provider;

        return this;
    }

    public void updateUserInfo(String nickname, String profileImg, String motherLand) {
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.motherLand = motherLand;
    }

}
