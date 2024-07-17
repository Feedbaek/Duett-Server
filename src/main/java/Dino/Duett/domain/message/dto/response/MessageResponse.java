package Dino.Duett.domain.message.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class MessageResponse {
    @NotNull
    private final Long senderId;
    @NotNull
    private final Long receiverId;
    @NotBlank
    private final String content;
    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String senderName;
}
