package Dino.Duett.domain.signup.controller;

import Dino.Duett.domain.signup.dto.SignUpReq;
import Dino.Duett.domain.signup.dto.SignUpRes;
import Dino.Duett.domain.signup.service.SignUpService;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "SignUp", description = "회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sign-up")
@Slf4j
public class SignUpController {
    private final SignUpService signUpService;

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "2000", description = "전화번호 중복(400)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "2001", description = "카카오 중복(400)", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "5003", description = "이미 존재하는 유저 이름(400)", content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JsonBody<SignUpRes> signUp(@Valid SignUpReq signUpReq) {
        return JsonBody.of(200, "회원가입 성공", signUpService.signUp(signUpReq));
    }

    @Operation(summary = "회원가입 Mock data, 인증 절차를 거치지 않습니다.")
    @PostMapping(value = "/mock", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JsonBody<SignUpRes> signUpMock(@Valid SignUpReq signUpReq) {
        return JsonBody.of(200, "회원가입 성공", signUpService.signUpMock(signUpReq));
    }
}

