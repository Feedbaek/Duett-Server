package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.profile.dto.request.ProfileInfoRequest;
import Dino.Duett.domain.profile.dto.request.ProfileIntroRequest;
import Dino.Duett.domain.profile.dto.response.ProfileInfoResponse;
import Dino.Duett.domain.profile.dto.response.ProfileIntroResponse;
import Dino.Duett.domain.tag.dto.response.TagByTypeResponse;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProfileApi {


    @Operation(summary = "내 정보 조회하기", tags = {"마이페이지"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
    })
    public JsonBody<ProfileInfoResponse> getProfileInfo(@AuthenticationPrincipal final AuthMember authMember);

    @Operation(summary = "내 정보 등록 및 수정하기", tags = {"마이페이지"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 인자 입력, 유효성 검사 실패", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
    })
    public JsonBody<Void> updateProfileInfo(@AuthenticationPrincipal final AuthMember authMember,
                                            @Validated @ModelAttribute final ProfileInfoRequest profileInfoRequest);

    @Operation(summary = "내 소개 조회하기", tags = {"마이페이지"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 소개 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "내 소개 조회 실패", content = @Content(schema = @Schema(hidden = true)))
    })
    public JsonBody<ProfileIntroResponse> getProfileIntro(@AuthenticationPrincipal final AuthMember authMember);

    @Operation(summary = "내 소개 등록 및 수정하기", tags = {"마이페이지"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 소개 등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 인자 입력", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "8000", description = "태그를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "8001", description = "태그 최대 개수 초과(400)", content = @Content(schema = @Schema(hidden = true))),

    })
    JsonBody<?> updateProfileIntro(@AuthenticationPrincipal final AuthMember authMember,
                                   @Validated @RequestBody final ProfileIntroRequest profileIntroRequest);

    @Operation(summary = "선택 가능한 모든 태그 조회하기", tags = {"마이페이지"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 태그 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
    })
    JsonBody<TagByTypeResponse> getAllTags(@AuthenticationPrincipal final AuthMember authMember);
}


