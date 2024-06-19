package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.mood.service.MoodService;
import Dino.Duett.domain.music.service.MusicService;
import Dino.Duett.domain.profile.dto.request.ProfileInfoRequest;
import Dino.Duett.domain.profile.dto.request.ProfileIntroRequest;
import Dino.Duett.domain.profile.dto.response.ProfileInfoResponse;
import Dino.Duett.domain.profile.dto.response.ProfileIntroResponse;
import Dino.Duett.domain.profile.dto.response.ProfileResponse;
import Dino.Duett.domain.profile.service.ProfileCardService;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.domain.tag.dto.response.TagByTypeResponse;
import Dino.Duett.domain.tag.service.ProfileTagService;
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
public class ProfileController implements ProfileApi{
    private final ProfileCardService profileCardService;
    private final MusicService musicService;
    private final MoodService moodService;
    private final ProfileService profileService;
    private final ProfileTagService profileTagService;

//    @Operation(summary = "자신의 음악 취향(인생곡과 무드) 조회", tags = {"마이페이지"}) // todo: 하나로 통일
//    @GetMapping("/profiles/music-taste")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "2002", description = "유저를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<ProfileMusicResponse> getProfileMusicAndMood(@AuthenticationPrincipal final AuthMember authMember){
//        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 취향(인생곡과 무드) 조회", profileCardService.getProfileMusic(authMember.getId()));
//    }

//    @Operation(summary = "자신의 음악 취향(인생곡과 무드) 한번에 추가, 수정, 삭제하기", tags = {"마이페이지"})
//    @PostMapping("/my-page/music-taste")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "2002", description = "유저를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<ProfileMusicResponse> createProfileMusicAndMood(@AuthenticationPrincipal final AuthMember authMember,
//                                                                    @ModelAttribute final ProfileMusicRequest request){
//        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 취향(인생곡과 무드) 추가, 수정, 삭제하기", myPageService.changeProfileMusic(authMember.getId(), request));
//    }
//
//    @Operation(summary = "자신의 무드 등록하기", tags = {"프로필카드"})
//    @PostMapping("/profiles/mood")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "400", description = "유저를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<MoodResponse> createMood(@AuthenticationPrincipal final AuthMember authMember,
//                                             @ModelAttribute final MoodCreateRequest moodCreateRequest){
//        return JsonBody.of(HttpStatus.OK.value(), "자신의 무드 등록하기", moodService.createMood(authMember.getId(), moodCreateRequest));
//    }
//
//    @Operation(summary = "자신의 음악 등록하기", tags = {"마이페이지"})
//    @PostMapping("/profiles/music")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "400", description = "유저를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<MusicResponse> createMusic(@AuthenticationPrincipal final AuthMember authMember,
//                                               @Validated @RequestBody final MusicCreateRequest musicCreateRequest){
//        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 등록하기", musicService.createMusic(authMember.getId(), musicCreateRequest));
//    }
//
//
//
//    @Operation(summary = "자신의 음악 삭제하기", tags = {"마이페이지"})
//    @DeleteMapping("/profiles/musics/{musicId}")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "400", description = "음악 취향 조회 실패", content = @Content(schema = @Schema(hidden = true))),
//    })
//    public JsonBody<Long> deleteMusic(@AuthenticationPrincipal final AuthMember authMember,
//                                      @PathVariable final Long musicId){
//        musicService.deleteMusic(authMember.getId() , musicId);
//        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 삭제하기", musicId);
//    }


//    @Operation(summary = "내 정보 조회하기", tags = {"마이페이지"})
    //todo: 구현에 따라 삭제예정

    //    @GetMapping("/profiles/info")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "400", description = "내 정보 조회 실패", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<ProfileInfoResponse> getProfile(@AuthenticationPrincipal final AuthMember authMember){
//        return JsonBody.of(HttpStatus.OK.value(), "내 정보 조회하기", profileService.getProfileInfo(authMember.getId()));
//    }
//    @Operation(summary = "내 소개 조회하기", tags = {"마이페이지"})
//    @GetMapping("/profiles/intro")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "내 소개 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "400", description = "내 소개 조회 실패", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<ProfileIntroResponse> getProfileIntro(@AuthenticationPrincipal final AuthMember authMember){
//        return JsonBody.of(HttpStatus.OK.value(), "내 소개 조회하기", profileService.getProfileIntro(authMember.getId()));
//    }
//
    //    @Operation(summary = "마이페이지 정보 전체 조회하기", tags = {"마이페이지"})
//    @GetMapping("/profiles")
//    @ApiResponses(value = {
//    @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
//            @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "400", description = "프로필 조회 실패", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<ProfileResponse> getProfile(@AuthenticationPrincipal final AuthMember authMember){
//        return JsonBody.of(HttpStatus.OK.value(), "내 프로필 카드 조회 성공", profileService.getProfile(authMember.getId()));
//    }
//    @Operation(summary = "내 정보 등록 및 수정하기", tags = {"마이페이지"})
//    @PostMapping("/profiles/info")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "내 정보 등록 성공"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
//            @ApiResponse(responseCode = "400", description = "내 정보 조회 실패", content = @Content(schema = @Schema(hidden = true)))
//    })
//    public JsonBody<ProfileInfoResponse> updateProfile(@AuthenticationPrincipal final AuthMember authMember,
//                                                       @Validated @ModelAttribute final ProfileInfoRequest profileInfoRequest){
//        return JsonBody.of(HttpStatus.OK.value(), "내 정보 수정하기", profileService.updateProfileInfo(authMember.getId(), profileInfoRequest));
//    }

    @Operation(summary = "내 소개 등록 및 수정하기", tags = {"마이페이지"})
    @PostMapping("/profiles/intro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 소개 등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 인자 입력", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "8000", description = "태그를 찾을 수 없음. 태그 잘못 입력(400)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "8002", description = "태그 최대 개수 초과(400)", content = @Content(schema = @Schema(hidden = true))),

    })
    public JsonBody<ProfileIntroResponse> updateProfileIntro(@AuthenticationPrincipal final AuthMember authMember,
                                                             @Validated @RequestBody final ProfileIntroRequest profileIntroRequest){
        return JsonBody.of(HttpStatus.OK.value(), "내 소개 수정하기", profileService.updateProfileIntro(authMember.getId(), profileIntroRequest));
    }

    @Operation(summary = "선택 가능한 모든 태그 조회하기", tags = {"마이페이지"})
    @GetMapping("/profiles/tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 태그 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(400)", content = @Content(schema = @Schema(hidden = true))),
    })
    public JsonBody<TagByTypeResponse> getAllTags(@AuthenticationPrincipal final AuthMember authMember) {
        return JsonBody.of(HttpStatus.OK.value(), "유저 태그 조회 성공", profileService.getProfileTagsWithAllTagsByMemberId(authMember.getId()));
    }
}
