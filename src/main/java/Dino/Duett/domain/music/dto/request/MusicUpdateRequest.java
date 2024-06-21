package Dino.Duett.domain.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "음악 수정 요청")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MusicUpdateRequest {
    @Schema(description = "음악 id", name = "musicId")
    private Long musicId;
    @Schema(description = "음악 제목", name = "title")
    @Size(max = 100)
    private String title;
    @Schema(description = "아티스트 이름", name = "artist")
    @Size(max = 100)
    private String artist;
    @Schema(description = "유튜브 URL", example = "url")
    private String url;
    public MusicUpdateRequest of(Long musicId,
                                 String title,
                                 String artist,
                                 String url) {
        return new MusicUpdateRequest(
                musicId,
                title,
                artist,
                url);
    }
}
