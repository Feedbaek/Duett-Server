package Dino.Duett.domain.profile.dto.response;

import lombok.Getter;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.profile.enums.MbtiType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "프로필 간략 응답")
@Getter
@Builder
public class ProfileCardBriefResponse {
    @Schema(description = "프로필 ID", example = "1")
    Long profileId;
    @Schema(description = "사용자의 이름", example = "name")
    String name;
    @Schema(description = "사용자의 생년월일", example = "2000.01.01")
    String birthDate;
    @Schema(description = "MBTI 유형", example = "ENTP")
    MbtiType mbti;
    @Schema(description = "인생곡", example = "[{\"title\": \"title\", \"artist\": \"artist\", \"url\": \"url\"}]")
    MusicResponse lifeMusic;
    @Schema(description = "태그", example = "[\"팝\", \"콘서트\"]")
    List<String> tags;
}

