package me.kimgunwoo.auctionseats.domain.user.repository;

import io.lettuce.core.dynamic.annotation.Param;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmailAndIsDeletedIsFalse(String email);

    Boolean existsByNicknameAndIsDeletedIsFalse(String nickname);


    Optional<User> findByEmailAndIsDeletedIsFalse(String email);

    Boolean existsByPhoneNumberAndIsDeletedIsFalse(String phoneNumber);
    // id를 사용하여 point 조회
    @Query("SELECT u.point FROM User u WHERE u.id = :userId")
    Long findPointById(@Param("userId") Long userId);
}
