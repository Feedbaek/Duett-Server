package Dino.Duett.domain.profile.dto.response;

import Dino.Duett.domain.profile.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Schema(description = "내 정보 조회 응답")
@Getter
@Builder
public class ProfileInfoResponse {
    @Schema(description = "프로필 이미지 URL", example = "https://duett.com/image.jpg")
    String profileImageUrl;
    @Schema(description = "닉네임", example = "crush")
    String name;
    @Schema(description = "생년월일", example = "2000.01.01")
    String birthDate;
    @Schema(description = "성별", example = "여성")
    String gender;
    @Schema(description = "프로필 한줄소개", example = "안녕하세요!")
    String oneLineIntroduction;
}
