package Dino.Duett.domain.profile.dto.response;

import Dino.Duett.domain.music.dto.response.MusicResponse;
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
    @Schema(description = "프로필 ID",example = "1")
    Long profileId;
    @Schema(description = "사용자의 이름", example = "name")
    String name;
    @Schema(description = "사용자의 생년월일", example = "2000.01.01")
    String birthDate;
    @Schema(description = "MBTI 유형", example = "ENTP")
    MbtiType mbti;
    @Schema(description = "한 줄 소개", example = "crush")
    String oneLineIntroduction;
    @Schema(description = "사용자와의 거리", example = "1.5")
    double distance;
    @Schema(description = "인생곡 리스트", example = "[{\"title\": \"title\", \"artist\": \"artist\", \"url\": \"url\"}]")
    List<MusicResponse> lifeMusics;
    @Schema(description = "태그", example = "[\"팝\", \"콘서트\"]")
    List<String> tags;
}
