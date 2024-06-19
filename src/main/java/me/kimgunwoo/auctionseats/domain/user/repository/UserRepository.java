package me.kimgunwoo.auctionseats.domain.user.repository;

import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmailAndIsDeletedIsFalse(String email);

    Boolean existsByNicknameAndIsDeletedIsFalse(String nickname);


    Optional<User> findByEmail(String email);
}
