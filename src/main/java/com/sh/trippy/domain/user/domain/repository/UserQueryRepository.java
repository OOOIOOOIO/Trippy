package com.sh.trippy.domain.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.trippy.domain.user.domain.QUsers;
import com.sh.trippy.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.trippy.domain.user.domain.QUsers.users;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Users> findByEmailAndProvider(String email, String provider) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(users)
                        .from(users)
                        .where(users.email.eq(email), users.provider.eq(provider))
                        .fetchOne()
        );
    }


}
