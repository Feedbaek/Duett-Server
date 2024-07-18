package Dino.Duett.domain.message.dto.response;

import Dino.Duett.domain.profile.dto.response.ProfileCardBriefResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "수신 메세지 리스트 응답")
@Getter
@RequiredArgsConstructor(staticName = "of")
public class MessageReceiveResponse {
    @Schema(description = "보낸 사람 프로파일 정보")
    @NotNull
    private final ProfileCardBriefResponse sender;

    @Schema(description = "받은 사람 ID", example = "2")
    @NotNull
    private final Long receiverId;

    @Schema(description = "메세지 내용", example = "안녕하세요")
    @NotBlank
    private final String content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String senderName;

    @Schema(description = "받은 메세지 날짜", example = "2024-07-16T05:46:26.499Z")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime messageDate;
}
