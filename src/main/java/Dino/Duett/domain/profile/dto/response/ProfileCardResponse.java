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
    @Schema(description = "프로필 ID", example = "1")
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
    Double distance;
    @Schema(description = "프로필 이미지 URL", example = "crush")
    String profileImageUrl;
    @Schema(description = "음악 태그", example = "[{\"name\": \"팝\", \"state\": \"FEATURED\"}, {\"name\": \"발라드\", \"state\": \"STANDARD\"}, {\"name\": \"힙합\", \"state\": \"STANDARD\"}]")
    List<TagResponse> musicTags;
    @Schema(description = "취미 태그", example = "[{\"name\": \"영화\", \"state\": \"STANDARD\"}, {\"name\": \"콘서트\", \"state\": \"STANDARD\"}, {\"name\": \"캠핑\", \"state\": \"STANDARD\"}]")
    List<TagResponse> hobbyTags;
    @Schema(description = "인생곡 리스트", example = "[{\"title\": \"title\", \"artist\": \"artist\", \"url\": \"url\"}]")
    List<MusicResponse> lifeMusics;
    @Schema(description = "mood 정보", example = "{\"title\": \"title\", \"artist\": \"artist\", \"moodImage\": \"https://duett-mood-image/image.jpg\", \"isDeleteImage\": \"true\" }")
    MoodResponse mood;
    @Schema(description = "긴 자기 소개", example = "crush")
    String selfIntroduction;
    @Schema(description = "호감을 느낄만한 상대의 음악취향", example = "crush")
    String likeableMusicTaste;
    @Schema(description = "좋아요", example = "true")
    Boolean likeState;
}