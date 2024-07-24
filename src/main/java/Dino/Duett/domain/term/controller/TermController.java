package Dino.Duett.domain.term.controller;

import Dino.Duett.domain.term.dto.response.TermResponse;
import Dino.Duett.domain.term.entity.Term;
import Dino.Duett.domain.term.service.TermService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Term", description = "회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/term")
@Slf4j
public class TermController {
    private final TermService termService;

    @Operation(summary = "가장 최근 약관 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "약관 조회 성공"),
    })
    @GetMapping(value = "")
    public JsonBody<TermResponse> getLatestTerm() {
        return JsonBody.of(200, "약관 조회 성공", termService.getLatestTerms(Term.TermsType.SIGN_UP));
    }

    @Operation(summary = "회원가입 약관 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 약관 생성 성공"),
    })
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public JsonBody<Void> createSignUpTerm() throws IOException {
        termService.createSignUpTerms();
        return JsonBody.of(200, "회원가입 약관 생성 성공", null);
    }
    @Operation(summary = "개인정보 처리방침 약관 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개인정보 처리방침 약관 생성 성공"),
    })
    @PostMapping(value = "/privacy-policy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public JsonBody<Void> createPrivacyPolicyTerm() throws IOException {
        termService.createPrivacyPolicyTerms();
        return JsonBody.of(200, "개인정보 처리방침 약관 생성 성공", null);
    }
}

