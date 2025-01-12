package Dino.Duett.domain.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "음악 변경 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MusicChangeRequest {
    @Schema(description = "인생곡 추가 리스트", nullable = true)
    List<MusicCreateRequest> createLifeMusics;
    @Schema(description = "인생곡 수정 리스트", nullable = true)
    List<MusicUpdateRequest> updateLifeMusics;
    @Schema(description = "인생곡 삭제 리스트", nullable = true)
    List<MusicDeleteRequest> deleteLifeMusics;
}
