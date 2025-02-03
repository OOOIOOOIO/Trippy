package com.sh.trippy.global.security;


import com.sh.trippy.domain.user.domain.Users;
import com.sh.trippy.domain.user.domain.repository.UserQueryRepository;
import com.sh.trippy.domain.user.domain.repository.UsersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Getter
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserQueryRepository usersQueryRepository;

    @Transactional
    public UserDetails loadUserByUsernameAndProvider(String email, String provider) throws UsernameNotFoundException {
        Users user = usersQueryRepository.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));


        return UserDetailsImpl.build(user);
    }

}
