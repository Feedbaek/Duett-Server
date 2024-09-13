package Dino.Duett.domain.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageSendRequest {
    @NotNull
    private Integer sendType;
    @NotNull
    private Long receiverId;
    private String content;

    @Builder
    public MessageSendRequest(Integer sendType, Long receiverId, String content) {
        this.sendType = sendType;
        this.receiverId = receiverId;
        this.content = content;
    }
}
