package Dino.Duett.domain.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "음악 등록 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MusicCreateRequest {
    @Schema(description = "음악 제목", name = "title")
    @Size(max = 100)
    @NotBlank
    private String title;
    @Schema(description = "아티스트 이름", name = "artist")
    @Size(max = 100)
    @NotBlank
    private String artist;
    @Schema(description = "유튜브 비디오 id", example = "Y4nEEZwckuU")
    @NotBlank
    private String videoId;
}
