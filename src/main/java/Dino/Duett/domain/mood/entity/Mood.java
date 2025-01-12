package Dino.Duett.domain.mood.entity;

import Dino.Duett.domain.image.entity.Image;
import Dino.Duett.global.utils.Validator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "mood")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mood_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String artist;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mood_image_id")
    private Image moodImage;

    public Mood(final Long id, final String title, final String artist, final Image moodImage) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.moodImage = moodImage;
    }

    public static Mood of(final String title, final String artist, final Image moodImage) {
        return new Mood(
                null,
                title,
                artist,
                moodImage
        );
    }

    public void updateMood(final String title, final String artist, final Image moodImage) {
        if(!Validator.isNullOrBlank(title)) {
            this.title = title;
        }

        if(!Validator.isNullOrBlank(artist)) {
            this.artist = artist;
        }

        if(moodImage != null) {
            this.moodImage = moodImage;
        }
    }
}
