package Dino.Duett.domain.music.dto.response;

import Dino.Duett.domain.music.entity.Music;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "음악 조회 응답")
@Getter
@AllArgsConstructor
public class MusicResponse {
    @Schema(description = "음악 id", name = "musicId")
    private Long musicId;
    @Schema(description = "음악 제목", name = "title")
    private String title;
    @Schema(description = "아티스트 이름", name = "artist")
    private String artist;
    @Schema(description = "유튜브 url", name = "url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;

    public static MusicResponse of(Music music){
        return new MusicResponse(
                music.getId(),
                music.getTitle(),
                music.getArtist(),
                music.getUrl());
    }
    public static MusicResponse of(Long musicId,
                                   String title,
                                   String artist){
        return new MusicResponse(
                musicId,
                title,
                artist,
                null);
    }
    public static List<MusicResponse> of(List<Music> musics){
        return musics.stream()
                .map(MusicResponse::of)
                .toList();
    }
}
