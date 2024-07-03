package Dino.Duett.domain.profile.entity;

import Dino.Duett.domain.image.entity.Image;
import Dino.Duett.domain.mood.entity.Mood;
import Dino.Duett.domain.music.entity.Music;
import Dino.Duett.domain.profile.enums.GenderType;
import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.tag.entity.ProfileTag;
import Dino.Duett.global.entity.BaseEntity;
import Dino.Duett.global.utils.Validator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    @Column(unique = true, length = 15)
    private String name;
    private String birthDate;
    @Enumerated(EnumType.STRING)
    private MbtiType mbti;
    @Column(length = 50)
    private String oneLineIntroduction;
    @Column(length = 500)
    private String selfIntroduction;
    @Column(length = 500)
    private String likeableMusicTaste;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @Column(nullable = false)
    private Boolean isProfileComplete;

    @Embedded
    private Location location;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileTag> profileTags = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mood_id")
    private Mood mood;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Music> musics = new ArrayList<>();

    @OneToMany(mappedBy = "viewerProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileUnlock> profileUnlocks = new ArrayList<>();

    @Builder
    public Profile(Long id, String name, String birthDate, MbtiType mbti, String oneLineIntroduction, String selfIntroduction, String likeableMusicTaste, GenderType gender, Boolean isProfileComplete, Location location, Image profileImage, List<ProfileTag> profileTags, Mood mood, List<Music> musics, List<ProfileUnlock> profileUnlocks) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.mbti = mbti;
        this.oneLineIntroduction = oneLineIntroduction;
        this.selfIntroduction = selfIntroduction;
        this.likeableMusicTaste = likeableMusicTaste;
        this.gender = gender;
        this.isProfileComplete = isProfileComplete;
        this.location = location;
        this.profileImage = profileImage;
        this.profileTags = profileTags;
        this.mood = mood;
        this.musics = musics;
        this.profileUnlocks = profileUnlocks;
    }

    public void updateIsProfileComplete(final Boolean isProfileComplete) {
        this.isProfileComplete = isProfileComplete;
    }

    public void updateProfileInfo(final Image image, final String name, final String oneLineIntroduction) {
        if (image != null) {
            this.profileImage = image;
        }
        if (!Validator.isNullOrBlank(name)) {
            this.name = name;
        }
        if (!Validator.isNullOrBlank(oneLineIntroduction)) {
            this.oneLineIntroduction = oneLineIntroduction;
        }
    }
    public void updateProfileIntro(final MbtiType mbti, final String selfIntroduction, final String likeableMusicTaste) {
        if (mbti != null) {
            this.mbti = mbti;
        }
        if (!Validator.isNullOrBlank(selfIntroduction)) {
            this.selfIntroduction = selfIntroduction;
        }
        if (!Validator.isNullOrBlank(likeableMusicTaste)) {
            this.likeableMusicTaste = likeableMusicTaste;
        }
    }

    public void addMood(final Mood mood) {
        this.mood = mood;
    }
}