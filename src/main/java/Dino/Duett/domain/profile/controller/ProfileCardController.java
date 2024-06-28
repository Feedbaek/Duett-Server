package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.profile.dto.response.ProfileCardResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardSummaryResponse;
import Dino.Duett.domain.profile.service.ProfileCardService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileCardController implements ProfileCardApi { //todo: 이후에 API 문서 위치 통일
    private final ProfileCardService profileCardService;

//    @GetMapping("/members/profile-cards") // todo: MVP 에서는 자신의 프로필 카드 조회 보류
//    public JsonBody<ProfileCardResponse> getProfileCard(@AuthenticationPrincipal final AuthMember authMember){
//        return JsonBody.of(HttpStatus.OK.value(), "내 프로필 카드 조회 성공", profileCardService.getProfileCard(authMember.getId()));
//    }

    @Operation(summary = "자신의 프로필 정보가 채워졌는지 조회", tags = {"프로필카드"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자신의 프로필 정보가 모두 채워졌는지 조회"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),

    })
    @GetMapping("/profile-cards/completion-status")
    public JsonBody<Boolean> getProfileCardCompletionStatus(@AuthenticationPrincipal final AuthMember authMember){
        return JsonBody.of(HttpStatus.OK.value(), "프로필의 정보가 채워졌는지 조회 성공", profileCardService.getProfileCardCompletionStatus(authMember.getId()));
    }

    @GetMapping("/profile-cards/{profileId}/coin")
    public JsonBody<ProfileCardResponse> getProfileCardOfDetailWithCoin(@AuthenticationPrincipal AuthMember authMember,
                                                                        @PathVariable final Long profileId){
        return JsonBody.of(HttpStatus.OK.value(),"코인을 사용해서 프로필카드 상세 단일 조회 성공", profileCardService.getProfileCardWithCoin(authMember.getId(), profileId));
    }

    @Operation(summary = "프로필카드 상세 단일 조회", tags = {"프로필카드"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필카드 상세 단일 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5001", description = "프로필에 접근할 권한 없음(403)", content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/profile-cards/{profileId}")
    public JsonBody<ProfileCardResponse> getProfileCardOfDetail(@AuthenticationPrincipal AuthMember authMember,
                                                                @PathVariable final Long profileId){
        return JsonBody.of(HttpStatus.OK.value(),"프로필카드 상세 단일 조회 성공", profileCardService.getProfileCard(authMember.getId(), profileId));
    }

    @GetMapping("/profile-cards/summary")
    public JsonBody<List<ProfileCardSummaryResponse>> getProfileCardsOfSummary(@AuthenticationPrincipal AuthMember authMember,
                                                                               @RequestParam final int page,
                                                                               @RequestParam final int size,
                                                                               @RequestParam final double radius){
        return JsonBody.of(HttpStatus.OK.value(), "반경 내의 프로필카드 요약 목록 조회 성공", profileCardService.getProfileCardsOfSummary(authMember.getId(), page, size, radius));
    }
}
