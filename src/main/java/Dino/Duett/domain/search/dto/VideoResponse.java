package Dino.Duett.domain.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VideoResponse {
    @Schema(description = "유튜브 비디오 id")
    private String videoId;
    @Schema(description = "유튜브 비디오 제목")
    private String title;
    @Schema(description = "유튜브 비디오 썸네일 관련 정보")
    private ThumbnailResponse thumbnail;
    @Schema(description = "유튜브 채널 이름")
    private String ChannelTitle;
}

