package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.profile.dto.response.ProfileCardBriefResponse;
import Dino.Duett.domain.profile.service.ProfileUnlockService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileUnlockController {
    private final ProfileUnlockService profileUnlockService;

    @Operation(summary = "열어본 프로필 목록 조회", tags = {"프로필카드"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "잠금해제한 프로필 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),

    })
    @GetMapping("/profile-cards/unlock")
    public JsonBody<List<ProfileCardBriefResponse>> getUnlockedProfiles(@AuthenticationPrincipal final AuthMember authMember,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return JsonBody.of(HttpStatus.OK.value(), "열어본 프로필 목록 조회 성공", profileUnlockService.getUnlockedProfiles(authMember.getId(), PageRequest.of(page, size)));
    }
}
