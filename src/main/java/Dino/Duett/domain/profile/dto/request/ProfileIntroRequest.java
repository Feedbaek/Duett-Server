package Dino.Duett.domain.profile.dto.request;

import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.tag.dto.request.TagRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "내 소개 등록 및 수정 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProfileIntroRequest {
    @Schema(description = "MBTI. 없음 체크 표시의 경우 NONE으로 설정", example = "ENFP", nullable = true)
    MbtiType mbti;
    @Schema(description = "음악 태그", example = "[{\"name\": \"팝\", \"state\": \"FEATURED\"}, {\"name\": \"발라드\", \"state\": \"STANDARD\"}, {\"name\": \"힙합\", \"state\": \"STANDARD\"}]", nullable = true)
    List<TagRequest> musicTags;
    @Schema(description = "취미 태그", example = "[{\"name\": \"영화\", \"state\": \"STANDARD\"}, {\"name\": \"콘서트\", \"state\": \"STANDARD\"}, {\"name\": \"캠핑\", \"state\": \"STANDARD\"}]", nullable = true)
    List<TagRequest> hobbyTags;
    @Schema(description = "긴 자기 소개", example = "안녕하세요!", maxLength = 500, nullable = true)
    @Size(max = 500)
    String selfIntroduction;
    @Schema(description = "호감을 느낄만한 상대의 음악취향",  example = "인디", maxLength = 500, nullable = true)
    @Size(max = 500)
    String likeableMusicTaste;
}
