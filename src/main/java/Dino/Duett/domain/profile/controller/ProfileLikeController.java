package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.profile.dto.response.ProfileCardBriefResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardSummaryResponse;
import Dino.Duett.domain.profile.dto.response.ProfileHomeResponse;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.service.ProfileLikeService;
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
public class ProfileLikeController {
    private final ProfileLikeService profileLikeService;

    @Operation(summary = "좋아요/좋아요해제 요청", tags = {"프로필카드"})
    @PostMapping("/profiles/like")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 좋아요 성공(좋아요 상태가 아니었을때)"),
            @ApiResponse(responseCode = "200", description = "프로필 좋아요 해제 성공(좋아요 상태였을때)"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5004", description = "자신의 프로필에 좋아요 불가(400)", content = @Content(schema = @Schema(hidden = true))),
    })
    // like profile
    public JsonBody<Boolean> likeOrUnlikeProfile(@AuthenticationPrincipal final AuthMember authMember, @RequestParam("profileId") Long profileId) {
        if (profileLikeService.likeOrUnlikeProfile(authMember.getMemberId(), profileId)) {
            return JsonBody.of(HttpStatus.OK.value(), "프로필 좋아요 성공", true);
        } else {
            return JsonBody.of(HttpStatus.OK.value(), "프로필 좋아요 취소 성공", false);
        }
    }

    @Operation(summary = "좋아요 한 프로필 목록 조회", tags = {"프로필카드"})
    @GetMapping("/profiles/like")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 좋아요 성공(좋아요 상태가 아니었을때)"),
            @ApiResponse(responseCode = "200", description = "프로필 좋아요 해제 성공(좋아요 상태였을때)"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5004", description = "자신의 프로필에 좋아요 불가(400)", content = @Content(schema = @Schema(hidden = true))),
    })
    // like profile
    public JsonBody<List<ProfileCardBriefResponse>> getLikedProfiles(
            @AuthenticationPrincipal final AuthMember authMember,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        return JsonBody.of(HttpStatus.OK.value(), "내가 좋아요한 프로필 목록 조회 성공", profileLikeService.getLikedProfiles(authMember.getMemberId(), page));
    }


    @Operation(summary = "나를 좋아요 한 프로필 목록 조회", tags = {"프로필카드"})
    @GetMapping("/profiles/liker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 좋아요 성공(좋아요 상태가 아니었을때)"),
            @ApiResponse(responseCode = "200", description = "프로필 좋아요 해제 성공(좋아요 상태였을때)"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5004", description = "자신의 프로필에 좋아요 불가(400)", content = @Content(schema = @Schema(hidden = true))),
    })
    // like profile
    public JsonBody<List<ProfileCardBriefResponse>> getLikers(
            @AuthenticationPrincipal final AuthMember authMember,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        return JsonBody.of(HttpStatus.OK.value(), "내 프로필에 좋아요한 프로파일 목록 조회 성공", profileLikeService.getMembersWhoLikedProfile(authMember.getMemberId(), page));
    }
}
