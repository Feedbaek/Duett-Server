package Dino.Duett.domain.message.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.message.dto.request.MessageDeleteRequest;
import Dino.Duett.domain.message.dto.request.MessageSendRequest;
import Dino.Duett.domain.message.dto.response.MessageDeleteResponse;
import Dino.Duett.domain.message.dto.response.MessageResponse;
import Dino.Duett.domain.message.service.MessageService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    // 모든 메시지 조회
    @GetMapping("/all")
    @Operation(summary = "모든 메시지 조회. paging 처리되어있음", tags = {"메시지"})
    @ApiResponse(responseCode = "200", description = "모든 메시지 조회 성공")
    public JsonBody<List<MessageResponse>> getAllMessages(
            @AuthenticationPrincipal final AuthMember authMember,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return JsonBody.of(HttpStatus.OK.value(), "모든 메시지 조회 성공", messageService.getAllMessages(authMember.getMemberId(), page));
    }

    // 메시지 전송
    @PostMapping("/send")
    @Operation(summary = "메시지 전송", tags = {"메시지"})
    @ApiResponse(responseCode = "200", description = "메시지 전송 성공")
    public JsonBody<MessageResponse> sendMessage(
            @AuthenticationPrincipal final AuthMember authMember,
            @Valid @RequestBody final MessageSendRequest messageSendRequest) {
        return JsonBody.of(HttpStatus.OK.value(), "메시지 전송 성공", messageService.sendMessage(authMember.getMemberId(), messageSendRequest));
    }

    // 받은 메시지 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "받은 메시지 삭제", tags = {"메시지"})
    @ApiResponse(responseCode = "200", description = "받은 메시지 삭제 성공")
    public JsonBody<MessageDeleteResponse> deleteMessage(
            @AuthenticationPrincipal final AuthMember authMember,
            @Valid @RequestBody final MessageDeleteRequest messageDeleteRequest) {
        return JsonBody.of(HttpStatus.OK.value(), "받은 메시지 삭제 성공", messageService.deleteMessage(authMember.getMemberId(), messageDeleteRequest));
    }
}
