package me.kimgunwoo.auctionseats.domain.user.entity;

import jakarta.persistence.*;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.constant.Role;
import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원 이메일")
    @Column(name = "email")
    private String email;

    @Comment("회원 비밀번호")
    @Column(name = "password")
    private String password;

    @Comment("회원 이름")
    @Column(name = "name")
    private String name;

    @Comment("회원 닉네임")
    @Column(name = "nickname")
    private String nickname;

    @Comment("회원 전화번호")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Comment("회원 생년월일")
    @Column(name = "birth")
    private LocalDate birth;

    @Comment("회원 역할(관리자 or 일반 유저)")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Comment("회원 보유 포인트")
    @Column(name = "point")
    @ColumnDefault("0")
    private long point;

    private User(String email, String password, String name, String nickname, String phoneNumber, LocalDate birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }

    public static User of(UserCreateRequest request, PasswordEncoder encoder) {
        return new User(
                request.email(),
                encoder.encode(request.password()),
                request.name(),
                request.nickname(),
                request.phoneNumber(),
                request.birth()
        );
    }
}
