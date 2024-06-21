package Dino.Duett.domain.profile.dto.response;

import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.tag.dto.response.TagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "프로필 카드 조회 요약 응답")
@Getter
@Builder
public class ProfileCardSummaryResponse {
    @Schema(description = "프로필 ID",example = "1", nullable = true)
    Long profileId;
    @Schema(description = "사용자의 이름", example = "name", nullable = true)
    String name;
    @Schema(description = "사용자의 나이", example = "crush", nullable = true)
    String age;
    @Schema(description = "MBTI 유형", example = "ENTP", nullable = true)
    MbtiType mbti;
    @Schema(description = "한 줄 소개", example = "crush", nullable = true)
    String oneLineIntroduction;
    @Schema(description = "사용자와의 거리", example = "1.5", nullable = true)
    double distance;
    @Schema(description = "프로필 이미지 URL", example = "crush", nullable = true)
    String profileImageUrl;
    @Schema(description = "태그", example = "[{\"name\": \"팝\", \"state\": \"FEATURED\"}, {\"name\": \"콘서트\", \"state\": \"FEATURED\"}", nullable = true)
    List<TagResponse> tags;
}
