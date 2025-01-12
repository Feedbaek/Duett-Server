package Dino.Duett.domain.profile.entity;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "profile_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liked_profile_id")
    private Profile likedProfile;

    @Builder
    public ProfileLike(Long id, Member member, Profile likedProfile) {
        this.id = id;
        this.member = member;
        this.likedProfile = likedProfile;
    }
}
