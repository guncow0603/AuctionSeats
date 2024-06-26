package me.kimgunwoo.auctionseats.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.entity.constant.Role;
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
    @Column(name = "email", length = 50)
    private String email;

    @Comment("회원 비밀번호")
    @Column(name = "password", length = 500)
    private String password;

    @Comment("회원 이름")
    @Column(name = "name", length = 10)
    private String name;
    @Comment("회원 닉네임")
    @Column(name = "nickname", length = 10)
    private String nickname;
    @Comment("회원 전화번호")
    @Column(name = "phone_number", length = 30)
    private String phoneNumber;
    @Comment("회원 생년월일")
    @Column(name = "birth")
    private LocalDate birth;
    @Comment("회원 역할(관리자 or 일반 유저)")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Comment("회원 보유 포인트")
    @Column(name = "point")
    @ColumnDefault("0")
    private Long point = 0L;

    @Comment("삭제 여부")
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Builder
    private User(Long id, String email, String password, String name, String nickname, String phoneNumber,
                 LocalDate birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }
    public void chargePoint(Long point) {
        this.point += point;
    }

    public void usePoint(Long point) {
        this.point -= point;
    }
}