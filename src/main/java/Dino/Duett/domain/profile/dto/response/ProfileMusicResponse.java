package Dino.Duett.domain.profile.dto.response;

import Dino.Duett.domain.mood.dto.response.MoodResponse;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "내 정보 조회 응답")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileMusicResponse {
    @Schema(description = "인생곡 리스트", example = "[{\"musicId\": 1, \"title\": \"title\", \"artist\": \"artist\", \"url\": \"https://www.youtube.com/watch?v=UUID\"}]")
    private List<MusicResponse> lifeMusics;
    @Schema(description = "mood 정보", example = "{\"title\": \"title\", \"artist\": \"artist\", \"moodImageUrl\": \"https://duett.com/image.jpg\"}")
    private MoodResponse mood;

    public static ProfileMusicResponse of(final List<MusicResponse> lifeMusics, final MoodResponse moodResponse) {
        return new ProfileMusicResponse(
                lifeMusics,
                moodResponse
        );
    }
}
