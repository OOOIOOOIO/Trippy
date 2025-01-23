package com.sh.trippy.domain.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.trippy.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
