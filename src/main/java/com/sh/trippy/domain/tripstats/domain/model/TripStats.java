package com.sh.trippy.domain.tripstats.domain.model;

import com.sh.trippy.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statsId;

    private Integer domesticCnt;
    private Integer abroadCnt;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;
}
