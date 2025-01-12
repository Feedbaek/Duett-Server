package Dino.Duett.domain.message.controller;

import Dino.Duett.config.security.AuthMember;
import Dino.Duett.domain.message.dto.request.MessageDeleteRequest;
import Dino.Duett.domain.message.dto.request.MessageSendRequest;
import Dino.Duett.domain.message.dto.response.MessageDeleteResponse;
import Dino.Duett.domain.message.dto.response.MessageReceiveResponse;
import Dino.Duett.domain.message.dto.response.MessageResponse;
import Dino.Duett.domain.message.dto.response.MessageSendResponse;
import Dino.Duett.domain.message.service.MessageService;
import Dino.Duett.global.dto.JsonBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    // 수신한 모든 메시지 조회
    @GetMapping("/receive/all")
    @Operation(summary = "수신한 모든 메시지 조회. paging 처리되어있음", tags = {"메시지"})
    @ApiResponse(responseCode = "200", description = "모든 수신 메시지 조회 성공")
    public JsonBody<List<MessageReceiveResponse>> getAllReceiveMessages(
            @AuthenticationPrincipal final AuthMember authMember,
            @RequestParam(name = "page", required = false, defaultValue = "0") @PositiveOrZero Integer page) {
        return JsonBody.of(HttpStatus.OK.value(), "모든 수신 메시지 조회 성공", messageService.getAllReceiveMessages(authMember.getMemberId(), page));
    }

    // 발신한 모든 메시지 조회
    @GetMapping("/send/all")
    @Operation(summary = "발신한 모든 메시지 조회. paging 처리되어있음", tags = {"메시지"})
    @ApiResponse(responseCode = "200", description = "모든 발신 메시지 조회 성공")
    public JsonBody<List<MessageSendResponse>> getAllSendMessages(
            @AuthenticationPrincipal final AuthMember authMember,
            @RequestParam(name = "page", required = false, defaultValue = "0") @PositiveOrZero Integer page) {
        return JsonBody.of(HttpStatus.OK.value(), "모든 발신 메시지 조회 성공", messageService.getAllSendMessages(authMember.getMemberId(), page));
    }

    // 메시지 전송
    @PostMapping("/send")
    @Operation(summary = "메시지 전송", tags = {"메시지"})
    @ApiResponse(responseCode = "200", description = "메시지 전송 성공")
    @ApiResponse(responseCode = "10000", description = "메세지 타입 오류", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "10001", description = "메세지 길이 초과", content = @Content(schema = @Schema(hidden = true)))
    public JsonBody<MessageResponse> sendMessage(
            @AuthenticationPrincipal final AuthMember authMember,
            @Valid @RequestBody final MessageSendRequest messageSendRequest) {
        return JsonBody.of(HttpStatus.OK.value(), "메시지 전송 성공", messageService.sendMessage(authMember.getMemberId(), messageSendRequest));
    }

    // 받은 메시지 삭제
    @DeleteMapping("")
    @Operation(summary = "받은 메시지 삭제", tags = {"메시지"})
    @ApiResponse(responseCode = "200", description = "받은 메시지 삭제 성공")
    public JsonBody<MessageDeleteResponse> deleteMessage(
            @AuthenticationPrincipal final AuthMember authMember,
            @Valid @RequestBody final MessageDeleteRequest messageDeleteRequest) {
        return JsonBody.of(HttpStatus.OK.value(), "받은 메시지 삭제 성공", messageService.deleteMessage(authMember.getMemberId(), messageDeleteRequest));
    }
}
