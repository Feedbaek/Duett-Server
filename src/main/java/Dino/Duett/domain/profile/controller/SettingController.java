package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.profile.dto.request.LocationRequest;
import Dino.Duett.domain.profile.service.LocationService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SettingController {
    private final LocationService locationService;

    @Operation(summary = "프로필 위치 수정 요청", tags = {"마이페이지 - 나의 Duett"})
    @PutMapping("/profiles/location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 위치 수정 성공")
    })
    public JsonBody<Void> updateProfileLocation(@AuthenticationPrincipal final AuthMember authMember,
                                                @Validated @RequestBody final LocationRequest locationRequest){
        locationService.updateLocation(authMember.getMemberId(), locationRequest);
        return JsonBody.of(HttpStatus.OK.value(), "프로필 위치 수정 성공", null);
    }
}
