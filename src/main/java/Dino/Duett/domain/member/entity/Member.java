package Dino.Duett.domain.member.entity;

import Dino.Duett.domain.member.enums.MemberState;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phoneNumber", nullable = false, unique = true, length = 15)
    private String phoneNumber;
    @Column(name = "kakaoId", nullable = false, unique = true, length = 50)
    private String kakaoId;
    @Column(name = "coin", nullable = false)
    private Integer coin;
    @Column(name = "state", nullable = false)
    private MemberState state;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "sms_agree")
    @ColumnDefault("false")
    private Boolean smsAgree;

    public void updateProfile(Profile profile){
        this.profile = profile;
    }

    public int updateCoin(int coin) {
        if (this.coin - coin < 0) {
            throw new MemberException.MemberCoinNotEnoughException();
        }
        return this.coin -= coin;
    }

    @Builder
    public Member(Long id, String phoneNumber, String kakaoId, Integer coin, MemberState state, Role role, Profile profile, Boolean smsAgree) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
        this.coin = coin;
        this.state = state;
        this.role = role;
        this.profile = profile;
        this.smsAgree = smsAgree != null ? smsAgree : false; // default false
    }
}
