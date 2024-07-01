package Dino.Duett.domain.profile.dto.response;

import Dino.Duett.domain.profile.enums.MbtiType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "마이페이지 홈 응답")
@Getter
@Builder
public class ProfileHomeResponse {
    @Schema(description = "프로필 이미지 URL", example = "https://duett.com/image.jpg")
    String profileImageUrl;
    @Schema(description = "사용자의 이름", example = "name")
    String name;
    @Schema(description = "사용자의 생년월일", example = "2000년 01월 01일")
    String birthDate;
    @Schema(description = "MBTI 유형", example = "ENTP")
    MbtiType mbti;
    @Schema(description = "내 정보 입력 완료 개수", example = "2")
    int infoCount;
    @Schema(description = "내 소개 입력 완료 개수", example = "2")
    int introCount;
    @Schema(description = "내 음악 취향 입력 완료 개수", example = "1")
    int musicCount;
}
