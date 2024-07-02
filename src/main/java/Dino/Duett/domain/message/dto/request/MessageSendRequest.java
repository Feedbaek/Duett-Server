package Dino.Duett.domain.message.dto.request;

import Dino.Duett.domain.message.entity.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageSendRequest {
    @NotNull
    private Long receiverId;
    @NotBlank
    private String content;

    @Builder
    public MessageSendRequest(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
    }
}
