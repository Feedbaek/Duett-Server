package Dino.Duett.domain.profile.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "잠금된 프로필카드 목록 조회 응답")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileLockResponse {
    @Schema(description = "요약된 프로필 카드 목록")
    List<ProfileCardSummaryResponse> profileCardSummaryResponses;
    @Schema(description = "코인", example = "0")
    Integer coin;
    @Schema(description = "자신의 프로필이 채워졌는지 여부. 쿼리 스트링에 checkProfileComplete가 true일 경우에만 제공", example = "true")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean isProfileComplete;

    public static ProfileLockResponse of(final List<ProfileCardSummaryResponse> profileCardSummaryResponses,
                                         final Integer coin,
                                         final Boolean isProfileComplete) {
        return new ProfileLockResponse(
                profileCardSummaryResponses,
                coin,
                isProfileComplete
        );
    }
}
