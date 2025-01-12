package Dino.Duett.domain.message.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Schema(description = "메세지 응답")
@Getter
@RequiredArgsConstructor(staticName = "of")
public class MessageResponse {
    @Schema(description = "보낸 사람 ID", example = "1")
    @NotNull
    private final Long senderId;

    @Schema(description = "받은 사람 ID", example = "2")
    @NotNull
    private final Long receiverId;

    @Schema(description = "메세지 내용", example = "안녕하세요")
    @NotBlank
    private final String content;
}

