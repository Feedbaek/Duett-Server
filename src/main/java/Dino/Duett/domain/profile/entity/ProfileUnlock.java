package Dino.Duett.domain.profile.entity;

import Dino.Duett.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_unlock", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"viewer_profile_id", "viewed_profile_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileUnlock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_unlock_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_profile_id")
    private Profile viewerProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewed_profile_id")
    private Profile viewedProfile;

    public ProfileUnlock(Long id,
                         Profile viewerProfile,
                         Profile viewedProfile) {
        this.id = id;
        this.viewerProfile = viewerProfile;
        this.viewedProfile = viewedProfile;
    }

    public static ProfileUnlock of(Profile viewerProfile,
                                   Profile viewedProfile) {
        return new ProfileUnlock(
                null,
                viewerProfile,
                viewedProfile);
    }
}
