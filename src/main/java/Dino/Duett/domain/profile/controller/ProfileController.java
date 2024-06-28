package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.profile.dto.request.ProfileInfoRequest;
import Dino.Duett.domain.profile.dto.request.ProfileIntroRequest;
import Dino.Duett.domain.profile.dto.request.ProfileMusicRequest;
import Dino.Duett.domain.profile.dto.response.ProfileHomeResponse;
import Dino.Duett.domain.profile.dto.response.ProfileInfoResponse;
import Dino.Duett.domain.profile.dto.response.ProfileIntroResponse;
import Dino.Duett.domain.profile.dto.response.ProfileMusicResponse;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.domain.tag.dto.response.TagByTypeResponse;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController implements ProfileApi{ //todo: 이후에 API 문서 위치 통일
    private final ProfileService profileService;

    @Operation(summary = "마이페이지 메인(유저 기본 정보, 프로필 진행 정도) 조회", tags = {"마이페이지"})
    @GetMapping("/profiles/home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
    })
    public JsonBody<ProfileHomeResponse> getProfileHome(@AuthenticationPrincipal final AuthMember authMember) {
        return JsonBody.of(HttpStatus.OK.value(), "마이페이지 프로필 진행 정도 조회", profileService.getProfileHome(authMember.getId()));
    }


    @Operation(summary = "자신의 음악 취향(인생곡과 무드) 조회", tags = {"마이페이지"})
    @GetMapping("/profiles/music-taste")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
    })
    public JsonBody<ProfileMusicResponse> getProfileMusicAndMood(@AuthenticationPrincipal final AuthMember authMember){
        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 취향(인생곡과 무드) 조회", profileService.getProfileMusic(authMember.getId()));
    }

    @Operation(summary = "자신의 음악 취향(인생곡과 무드) 한번에 추가, 수정, 삭제하기", tags = {"마이페이지"})
    @PostMapping(value = "/profiles/music-taste", consumes = "multipart/form-data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "6001", description = "최대 인생곡 개수 도달(400)", content = @Content(schema = @Schema(hidden = true))),
    })
    public JsonBody<Void> changeProfileMusicAndMood(@AuthenticationPrincipal final AuthMember authMember,
                                                    @ModelAttribute final ProfileMusicRequest request){
        profileService.updateProfileMusic(authMember.getId(), request);
        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 취향(인생곡과 무드) 추가, 수정, 삭제하기", null);
    }

    @GetMapping("/profiles/info")
    public JsonBody<ProfileInfoResponse> getProfileInfo(@AuthenticationPrincipal final AuthMember authMember){
        return JsonBody.of(HttpStatus.OK.value(), "내 정보 조회 성공", profileService.getProfileInfo(authMember.getId()));
    }

    @PatchMapping(value = "/profiles/info", consumes = "multipart/form-data")
    public JsonBody<Void> updateProfileInfo(@AuthenticationPrincipal final AuthMember authMember,
                                        @Validated @ModelAttribute final ProfileInfoRequest profileInfoRequest) {
        profileService.updateProfileInfo(authMember.getId(), profileInfoRequest);
        return JsonBody.of(HttpStatus.OK.value(), "내 정보 수정 성공", null);
    }

    @GetMapping("/profiles/intro")
    public JsonBody<ProfileIntroResponse> getProfileIntro(@AuthenticationPrincipal final AuthMember authMember){
        return JsonBody.of(HttpStatus.OK.value(), "내 소개 조회 성공", profileService.getProfileIntro(authMember.getId()));
    }

    @PatchMapping("/profiles/intro")
    public JsonBody<Void> updateProfileIntro(@AuthenticationPrincipal final AuthMember authMember,
                                                             @Validated @RequestBody final ProfileIntroRequest profileIntroRequest){
        profileService.updateProfileIntro(authMember.getId(), profileIntroRequest);
        return JsonBody.of(HttpStatus.OK.value(), "내 소개 등록 및 수정 성공", null);
    }

    @GetMapping("/profiles/tags")
    public JsonBody<TagByTypeResponse> getAllTags(@AuthenticationPrincipal final AuthMember authMember) {
        return JsonBody.of(HttpStatus.OK.value(), "유저 태그 조회 성공", profileService.getProfileTagsWithAllTagsByMemberId(authMember.getId()));
    }
}
