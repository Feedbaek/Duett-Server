package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.profile.dto.response.ProfileCardResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardSummaryResponse;
import Dino.Duett.domain.profile.service.ProfileCardService;
import Dino.Duett.global.dto.JsonBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileCardController implements ProfileCardApi {
    private final ProfileCardService profileCardService;

//    @GetMapping("/members/profile-cards") // todo: MVP 에서는 자신의 프로필 카드 조회 보류
//    public JsonBody<ProfileCardResponse> getProfileCard(@AuthenticationPrincipal final AuthMember authMember){
//        return JsonBody.of(HttpStatus.OK.value(), "내 프로필 카드 조회 성공", profileCardService.getProfileCard(authMember.getId()));
//    }

    @GetMapping("/profile-cards/{profileId}/coin")
    public JsonBody<ProfileCardResponse> getProfileCardOfDetailWithCoin(@AuthenticationPrincipal AuthMember authMember,
                                                                        @PathVariable final Long profileId){
        return JsonBody.of(HttpStatus.OK.value(),"코인을 사용해서 프로필카드 상세 단일 조회 성공", profileCardService.getProfileCardWithCoin(authMember.getId(), profileId));
    }

    @GetMapping("/profile-cards/summary")
    public JsonBody<List<ProfileCardSummaryResponse>> getProfileCardsOfSummary(@AuthenticationPrincipal AuthMember authMember,
                                                                               @RequestParam final int page,
                                                                               @RequestParam final int size,
                                                                               @RequestParam final double radius){
        return JsonBody.of(HttpStatus.OK.value(), "반경 내의 프로필카드 요약 목록 조회 성공", profileCardService.getProfileCardsOfSummary(authMember.getId(), page, size, radius));
    }
}
