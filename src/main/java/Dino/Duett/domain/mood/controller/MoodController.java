package Dino.Duett.domain.mood.controller;


import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.mood.dto.request.MoodRequest;
import Dino.Duett.domain.mood.service.MoodService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MoodController {
    private final MoodService moodService;

    @Operation(summary = "자신의 무드 등록 및 수정", tags = {"테스트"})
    @PostMapping(value = "/profiles/moods", consumes = "multipart/form-data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 취향 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2003", description = "사용자를 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5000", description = "프로필을 찾을 수 없음(404)", content = @Content(schema = @Schema(hidden = true))),
    })
    public JsonBody<Void> changeProfileMusicAndMood(@AuthenticationPrincipal final AuthMember authMember,
                                                    @ModelAttribute final MoodRequest request) {
        moodService.changeMood(authMember.getMemberId(), request);
        return JsonBody.of(HttpStatus.OK.value(), "자신의 음악 취향(인생곡과 무드) 추가, 수정, 삭제하기", null);
    }
}
