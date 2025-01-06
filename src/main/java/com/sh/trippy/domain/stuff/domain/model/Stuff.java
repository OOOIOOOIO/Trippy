package com.sh.trippy.domain.stuff.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.sharechecklist.domain.model.ShareChecklist;
import com.sh.trippy.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stuff extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stuffId;
    private String stuff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shareChecklist")
    private ShareChecklist shareChecklist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private Users users;
}
