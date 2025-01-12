package Dino.Duett.domain.signup.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.signup.dto.request.WithdrawalReq;
import Dino.Duett.domain.signup.service.WithdrawalService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Withdrawal", description = "회원탈퇴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/withdrawal")
@Slf4j
public class WithdrawalController {
    private final WithdrawalService withdrawalService;

    @Operation(summary = "회원탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "2003", description = "멤버를 찾을 수 없음", content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public JsonBody<Boolean> withdrawal(
            @AuthenticationPrincipal final AuthMember authMember,
            @Valid @RequestBody WithdrawalReq withdrawalReq) {
        return JsonBody.of(200, "회원탈퇴 성공", withdrawalService.withdrawal(authMember.getPhoneNumber(), withdrawalReq));
    }
}
