package me.kimgunwoo.auctionseats.global.security;

import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_USER;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_USER_FOR_LOGIN;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndIsDeletedIsFalse(username).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER_FOR_LOGIN)
        );

        return new UserDetailsImpl(user);
    }
}