package Dino.Duett.domain.tag.entity;

import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.tag.enums.TagState;
import Dino.Duett.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_tag_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TagState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;


    public ProfileTag(final Long id, final TagState state, final Tag tag){
        this.id = id;
        this.state = state;
        this.tag = tag;
    }

    public static ProfileTag of(final TagState state, final Profile profile, final Tag tag) {
        ProfileTag profileTag = new ProfileTag(null, state, tag);
        profileTag.addProfile(profile); // 연관관계 매핑

        return profileTag;
    }

    public void updateState(final TagState state){
        this.state = state;
    }

    // 연관관계 편의 메서드
    public void addProfile(Profile profile) {
        if (this.profile != null) {
            this.profile.getProfileTags().remove(this);
        }
        this.profile = profile;
        profile.getProfileTags().add(this);
    }
    public void removeProfile(Profile profile) {
        profile.getProfileTags().remove(this);
        this.profile = null;
    }
}
