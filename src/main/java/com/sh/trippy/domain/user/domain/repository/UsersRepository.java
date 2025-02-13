package com.sh.trippy.domain.user.domain.repository;

import com.sh.trippy.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByNickname(String nickname);
}
