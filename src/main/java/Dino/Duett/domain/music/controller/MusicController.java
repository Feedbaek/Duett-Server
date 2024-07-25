package Dino.Duett.domain.music.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.music.dto.request.MusicChangeRequest;
import Dino.Duett.domain.music.service.MusicService;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MusicController {
    private final MusicService musicService;
    private final ProfileService profileService;

    @Operation(summary = "자신의 인생곡 한번에 추가, 수정, 삭제하기", tags = {"마이페이지 - 음악 취향"})
    @PostMapping("/profiles/musics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "6001", description = "최대 인생곡 개수 도달(400)", content = @Content(schema = @Schema(hidden = true))),

    })
    public JsonBody<Void> changeProfileMusic(@AuthenticationPrincipal final AuthMember authMember,
                                                    @RequestBody final MusicChangeRequest request){
        Profile profile = musicService.changeMusics(
                authMember.getMemberId(),
                request.getCreateLifeMusics(),
                request.getUpdateLifeMusics(),
                request.getDeleteLifeMusics());
        profileService.updateProfileCompleteStatusOnFirstFill(profile);
        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 취향(인생곡과 무드) 추가, 수정, 삭제하기", null);
    }
}
