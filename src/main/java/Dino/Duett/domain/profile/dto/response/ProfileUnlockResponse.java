package Dino.Duett.domain.profile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "잠금해제된 프로필카드 단일 조회 응답")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileUnlockResponse {
    @Schema(description = "프로필 카드")
    ProfileCardResponse profileCardResponse;
    @Schema(description = "코인", example = "0")
    Integer coin;

    public static ProfileUnlockResponse of(final ProfileCardResponse profileCardResponse,
                                           final Integer coin) {
        return new ProfileUnlockResponse(
                profileCardResponse,
                coin
        );
    }
}
