package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.profile.dto.response.*;
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
public class ProfileCardController {
    private final ProfileCardService profileCardService;
//
//    @Operation(summary = "내 프로필 카드 조회하기", tags = {"프로필카드"})
//    @GetMapping("/members/profile-cards")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "프로필카드 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true)))
//            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true)))
//            })
//    public JsonBody<ProfileCardResponse> getProfileCard(@AuthenticationPrincipal final AuthMember authMember){
//        return JsonBody.of(HttpStatus.OK.value(), "내 프로필 카드 조회 성공", profileCardService.getProfileCard(authMember.getId()));
//    }
//
//    @Operation(summary = "코인을 사용해서 프로필카드 상세 단일 조회하기", tags = {"프로필카드"})
//    @GetMapping("/profile-cards/{profileId}/coin")
//            @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "프로필카드 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true)))
//            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "5001", description = "접근 권한 없는 유저의 프로필 접근(403)", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "2005", description = "코인 부족(402)", content = @Content(schema = @Schema(hidden = true))),
//    })
//    public JsonBody<ProfileCardResponse> getProfileCardOfDetailWithCoin(@AuthenticationPrincipal AuthMember authMember,
//                                                                        @PathVariable final Long profileId){
//        return JsonBody.of(HttpStatus.OK.value(),"코인을 사용해서 프로필카드 상세 단일 조회 성공", profileCardService.getProfileCardWithCoin(authMember.getId(), profileId));
//    }
//
//    @Operation(summary = "반경 내의 프로필카드 요약 목록 조회하기", tags = {"프로필카드"})
//    @GetMapping("/profile-cards/summary")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "프로필카드 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),})
//    public JsonBody<List<ProfileCardSummaryResponse>> getProfileCardsOfSummary(@AuthenticationPrincipal AuthMember authMember,
//                                                                               @RequestParam final int page,
//                                                                               @RequestParam final int size,
//                                                                               @RequestParam final double radius){
//        return JsonBody.of(HttpStatus.OK.value(), "반경 내의 프로필카드 요약 목록 조회 성공", profileCardService.getProfileCardsOfSummary(authMember.getId(), page, size, radius));
//    }
}
