package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.profile.dto.response.ProfileCardBriefResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardSummaryResponse;
import Dino.Duett.domain.profile.dto.response.ProfileHomeResponse;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.service.ProfileLikeService;
import Dino.Duett.global.dto.JsonBody;
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

    @PostMapping("/profiles/like")
    // like profile
    public JsonBody<Boolean> likeOrUnlikeProfile(@AuthenticationPrincipal final AuthMember authMember, @RequestParam("profileId") Long profileId) {
        if (profileLikeService.likeOrUnlikeProfile(authMember.getMemberId(), profileId)) {
            return JsonBody.of(HttpStatus.OK.value(), "프로필 좋아요 성공", true);
        } else {
            return JsonBody.of(HttpStatus.OK.value(), "프로필 좋아요 취소 성공", false);
        }
    }

    @GetMapping("/profiles/like")
    // like profile
    public JsonBody<List<ProfileCardBriefResponse>> getLikedProfiles(@AuthenticationPrincipal final AuthMember authMember) {
        return JsonBody.of(HttpStatus.OK.value(), "내가 좋아요한 프로필 목록 조회 성공", profileLikeService.getLikedProfiles(authMember.getMemberId()));
    }

    @GetMapping("/profiles/liker")
    // like profile
    public JsonBody<List<ProfileCardBriefResponse>> getLikers(@AuthenticationPrincipal final AuthMember authMember) {
        return JsonBody.of(HttpStatus.OK.value(), "내 프로필에 좋아요한 프로파일 목록 조회 성공", profileLikeService.getMembersWhoLikedProfile(authMember.getMemberId()));
    }
}
