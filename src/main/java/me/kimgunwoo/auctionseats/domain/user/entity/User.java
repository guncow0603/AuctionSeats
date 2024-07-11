package me.kimgunwoo.auctionseats.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.enums.Role;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원 이메일")
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Comment("회원 비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    @Comment("회원 이름")
    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Comment("회원 닉네임")
    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Comment("회원 전화번호")
    @Column(name = "phone_number", length = 30, nullable = false)
    private String phoneNumber;

    @Comment("회원 생년월일")
    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Comment("회원 역할(관리자 or 일반 유저)")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Comment("회원 보유 포인트")
    @Column(name = "point", nullable = false)
    @ColumnDefault("0")
    private Long point = 0L;

    @Comment("삭제 여부")
    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @Builder
    private User(
            Long id,
            String email,
            String password,
            String name,
            String nickname,
            String phoneNumber,
            LocalDate birth,
            Role role
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.role = role;
    }

    public void chargePoint(Long point) {
        this.point += point;
    }

    public void usePoint(Long point) {
        this.point -= point;
    }

    public void updateUserNickName(String nickname) {
        this.nickname = nickname;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
    public void delete() {
        this.isDeleted = true;
    }
}