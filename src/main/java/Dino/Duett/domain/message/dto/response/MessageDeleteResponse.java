package Dino.Duett.domain.message.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class MessageDeleteResponse {
    @NotNull
    private final Long[] messageIds;
}
