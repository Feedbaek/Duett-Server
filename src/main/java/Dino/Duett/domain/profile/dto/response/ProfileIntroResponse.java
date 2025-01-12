package Dino.Duett.domain.profile.dto.response;

import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.tag.dto.response.TagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "내 소개 조회 응답")
@Getter
@Builder
public class ProfileIntroResponse {
    @Schema(description = "MBTI", example = "ENFP")
    MbtiType mbti;
    @Schema(description = "음악 태그", example = "[{\"name\": \"팝\", \"state\": \"FEATURED\"}, {\"name\": \"발라드\", \"state\": \"STANDARD\"}, {\"name\": \"힙합\", \"state\": \"STANDARD\"}]")
    List<TagResponse> musicTags;
    @Schema(description = "취미 태그", example = "[{\"name\": \"영화\", \"state\": \"STANDARD\"}, {\"name\": \"콘서트\", \"state\": \"STANDARD\"}, {\"name\": \"캠핑\", \"state\": \"STANDARD\"}]")
    List<TagResponse> hobbyTags;
    @Schema(description = "자기소개", example = "안녕하세요!")
    String selfIntroduction;
    @Schema(description = "호감을 느낄만한 상대의 음악취향", example = "인디")
    String likeableMusicTaste;
}
