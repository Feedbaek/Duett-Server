package Dino.Duett.domain.profile.dto.response;

import Dino.Duett.domain.mood.dto.response.MoodResponse;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.tag.dto.response.TagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "프로필 카드 조회 전체 응답")
@Getter
@Builder
public class ProfileCardResponse {
    @Schema(description = "프로필 ID", example = "1", nullable = true)
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
    @Schema(description = "음악 태그", example = "[{\"name\": \"팝\", \"state\": FEATURED}, {\"name\": \"발라드\", \"state\": STANDARD}, {\"name\": \"힙합\", \"state\": STANDARD}]", nullable = true)
    List<TagResponse> musicTags;
    @Schema(description = "취미 태그", example = "[{\"name\": \"영화\", \"state\": STANDARD}, {\"name\": \"콘서트\", \"state\": STANDARD}, {\"name\": \"캠핑\", \"state\": STANDARD}]", nullable = true)
    List<TagResponse> hobbyTags;
    @Schema(description = "인생곡 리스트", example = "[{\"title\": \"title\", \"artist\": \"artist\", \"musicUrl\": \"musicUrl\"}]", nullable = true)
    List<MusicResponse> lifeMusics;
    @Schema(description = "무드 정보", example = "crush", nullable = true)
    MoodResponse mood;
    @Schema(description = "긴 자기 소개", example = "crush", nullable = true)
    String selfIntroduction;
    @Schema(description = "호감을 느낄만한 상대의 음악취향", example = "crush", nullable = true)
    String likeableMusicTaste;
}