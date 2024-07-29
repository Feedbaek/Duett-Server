package Dino.Duett.domain.profile.dto.request;

import Dino.Duett.domain.mood.dto.request.MoodRequest;
import Dino.Duett.domain.music.dto.request.MusicCreateRequest;
import Dino.Duett.domain.music.dto.request.MusicDeleteRequest;
import Dino.Duett.domain.music.dto.request.MusicUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.MediaType;

import java.util.List;

@Schema(description = "음악 취향 수정 요청", type = MediaType.MULTIPART_FORM_DATA_VALUE)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProfileMusicTasteRequest {
    @Schema(description = "인생곡 추가 리스트", nullable = true)
    List<MusicCreateRequest> createLifeMusics;
    @Schema(description = "인생곡 수정 리스트", nullable = true)
    List<MusicUpdateRequest> updateLifeMusics;
    @Schema(description = "인생곡 삭제 리스트", nullable = true)
    List<MusicDeleteRequest> deleteLifeMusics;
    @Schema(description = "mood 정보", nullable = true)
    MoodRequest mood;
}
