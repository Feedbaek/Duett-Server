package Dino.Duett.domain.profile.dto.request;

import Dino.Duett.domain.mood.dto.request.MoodRequest;
import Dino.Duett.domain.music.dto.request.MusicCreateRequest;
import Dino.Duett.domain.music.dto.request.MusicUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "음악 취향 수정 요청", type = "multipartForm")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProfileMusicRequest {
    @Schema(description = "음악 추가 리스트", example = "[{\"title\": \"title\", \"artist\": \"artist\", \"musicUrl\": \"musicUrl\"}]", nullable = true)
    List<MusicCreateRequest> createLifeMusics;
    @Schema(description = "음악 수정 리스트", example = "[{\"musicId\": 1, \"title\": \"title\", \"artist\": \"artist\", \"musicUrl\": \"musicUrl\"}]", nullable = true)
    List<MusicUpdateRequest> updateLifeMusics;
    @Schema(description = "음악 삭제 리스트", example = "[{\"musicId\": 1}]", nullable = true)
    List<MusicUpdateRequest> deleteLifeMusics;
    @Schema(description = "mood 정보", example = "{\"title\": \"title\", \"artist\": \"artist\", \"moodImage\": \"https://duett-mood-image/image.jpg\", \"isDeleteImage\": \"true\" }", nullable = true)
    MoodRequest mood;
}
