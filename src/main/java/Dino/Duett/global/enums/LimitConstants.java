package Dino.Duett.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LimitConstants {
    PROFILE_TAG_MAX_LIMIT(3),
    FEATURED_PROFILE_TAG_MAX_LIMIT(1),
    YOUTUBE_SEARCH_LIMIT(10),
    MUSIC_MAX_LIMIT(3),
    PROFILE_INTRO_MIN_SIZE(2),
    PROFILE_MUSIC_TASTE_MIN_SIZE(1),
    PROFILE_MAX_LIMIT(10),
    MESSAGE_MAX_LIMIT(10),
    ;

    private final int limit;
}
