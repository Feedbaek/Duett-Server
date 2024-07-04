package Dino.Duett.domain.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "음악 수정 요청")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MusicDeleteRequest {
    @Schema(description = "음악 id", name = "musicId")
    @NotEmpty
    private Long musicId;
}
