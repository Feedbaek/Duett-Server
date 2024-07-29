package Dino.Duett.domain.music.entity;

import Dino.Duett.domain.music.dto.request.MusicUpdateRequest;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.global.entity.BaseEntity;
import Dino.Duett.global.utils.Validator;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "music")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Music extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String artist;
    @Column(nullable = false)
    private String videoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public static Music of(final String title,
                           final String artist,
                           final String videoId,
                           final Profile profile) {
        return new Music(
                null,
                title,
                artist,
                videoId,
                profile
        );
    }

    public void updateMusic(final MusicUpdateRequest musicUpdateRequest) {
        if (!Validator.isNullOrBlank(musicUpdateRequest.getTitle())){
            this.title = musicUpdateRequest.getTitle();
        }
        if (!Validator.isNullOrBlank(musicUpdateRequest.getArtist())) {
            this.artist = musicUpdateRequest.getArtist();
        }
        if (!Validator.isNullOrBlank(musicUpdateRequest.getVideoId())) {
            this.videoId = musicUpdateRequest.getVideoId();
        }
    }
}
