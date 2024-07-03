package Dino.Duett.domain.message.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDeleteRequest {
    @NotNull
    private Long[] messageIds;

    @Builder
    public MessageDeleteRequest(Long[] messageIds) {
        this.messageIds = messageIds;
    }
}
